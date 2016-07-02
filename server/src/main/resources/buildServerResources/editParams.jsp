<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="serverConfigModel" class="website.automate.teamcity.server.global.ServerConfigModel"/>

<script type="text/javascript">
    (function($){

        var aw = {};

        aw.selectedAccount = $.trim("${propertiesBean.properties['website.automate.teamcity.account']}");
        aw.selectedProject = $.trim("${propertiesBean.properties['website.automate.teamcity.project']}");
        aw.selectedScenarios = $.trim("${propertiesBean.properties['website.automate.teamcity.selectedScenarios']}").split(',');

        aw.accounts = ${serverConfigModel.accountsAsJson};
       
        aw.findProjectsByAccountId = function(accountId){
            var account = _.find(aw.accounts, function(account){
                return account.id === accountId;
            }) || _.first(aw.accounts);

            return _.property('projects')(account);
        };

        aw.findScenariosByAccountIdAndProjectId = function(accountId, projectId){
            var projects = aw.findProjectsByAccountId(accountId);
            var project = _.find(projects, function(project){
                return project.id === projectId;
            }) || _.first(projects);

            return _.property('scenarios')(project);
        };

        aw.populateSelect = function($select, entities, selectedValues){
            selectedValues = _.isArray(selectedValues) ? selectedValues : [selectedValues];
            $select.empty();
            _.each(entities, function(entity){
                var $option = $("<option></option>")
                        .val(entity.id)
                        .text(entity.title || entity.username || entity.name);
                if(selectedValues && _.contains(selectedValues, entity.id)){
                    $option.attr('selected', 'selected');
                }
                $select.append($option);
            });
        };

        $(function() {
            aw.$selectAccount = $('select#website\\.automate\\.teamcity\\.account');
            aw.$selectProject = $('select#website\\.automate\\.teamcity\\.project');
            aw.$selectScenario = $('select#website\\.automate\\.teamcity\\.scenario');
            aw.$inputSelectedScenarios = $('input#website\\.automate\\.teamcity\\.selectedScenarios');

            aw.$selectScenario.change(function(){
                var selectedScenarios = [];
                $('option:selected', aw.$selectScenario).each(function() {
                  selectedScenarios.push($( this ).val());
                });
                aw.selectedScenarios = selectedScenarios.join(',');
                aw.$inputSelectedScenarios.val(aw.selectedScenarios);
            });

            aw.$selectAccount.change(function(){
                var selectedAccount = '';
                $('option:selected', aw.$selectAccount).each(function() {
                  selectedAccount = $( this ).val();
                  return false;
                });
                aw.selectedAccount = selectedAccount;

                aw.populateSelect(aw.$selectProject, aw.findProjectsByAccountId(aw.selectedAccount), aw.selectedProject);
                aw.populateSelect(aw.$selectScenario, aw.findScenariosByAccountIdAndProjectId(aw.selectedAccount, aw.selectedProject), aw.selectedScenarios);

                aw.$inputSelectedScenarios.val('');
            });

            aw.$selectProject.change(function(){
                var selectedProject = '';
                $('option:selected', aw.$selectProject).each(function() {
                  selectedProject = $( this ).val();
                  return false;
                });
                aw.selectedProject = selectedProject;
                
                aw.populateSelect(aw.$selectScenario, aw.findScenariosByAccountIdAndProjectId(aw.selectedAccount, aw.selectedProject), aw.selectedScenarios);

                aw.$inputSelectedScenarios.val('');
            });


            aw.populateSelect(aw.$selectAccount, aw.accounts, aw.selectedAccount);
            aw.populateSelect(aw.$selectProject, aw.findProjectsByAccountId(aw.selectedAccount), aw.selectedProject);
            aw.populateSelect(aw.$selectScenario, aw.findScenariosByAccountIdAndProjectId(aw.selectedAccount, aw.selectedProject), aw.selectedScenarios);
        });
    })(jQuery);    
</script>

<l:settingsGroup title="Scenario Execution Selection">
    <tr>
        <th>
            <label for="website.automate.teamcity.account">Account</label>
        </th>
        <td>
            <props:selectProperty name="website.automate.teamcity.account" />
        </td>
    </tr>
    <tr>
        <th>
            <label for="website.automate.teamcity.project">Project</label>
        </th>
        <td>
            <props:selectProperty name="website.automate.teamcity.project" />
        </td>
    </tr>
    <tr>
        <th>
            <label for="website.automate.teamcity.scenario">Scenarios</label>
        </th>
        <td>
            <props:selectProperty name="website.automate.teamcity.scenario" multiple="true" />
            <props:hiddenProperty name="website.automate.teamcity.selectedScenarios" value=""/>
        </td>
    </tr>
</l:settingsGroup>
