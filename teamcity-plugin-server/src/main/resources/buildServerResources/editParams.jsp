<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<script type="text/javascript">
    (function($){
        var aw = {};

        aw.selectedAccount = $.trim('2');
        aw.selectedProject = $.trim('21');
        aw.selectedScenarios = $.trim('211,212').split(',');

        aw.accounts = [
            { 
                id: '1', 
                username : 'Account 1', 
                projects : [
                    { 
                        id: '11', 
                        title: 'Project 11',
                        scenarios: [
                            {
                                id: '111',
                                title: 'Scenario 111'
                            },
                            {
                                id: '112',
                                title: 'Scenario 112'
                            }
                        ]
                    },
                    { 
                        id: '12', 
                        title: 'Project 12',
                        scenarios: [
                            {
                                id: '121',
                                title: 'Scenario 121'
                            },
                            {
                                id: '122',
                                title: 'Scenario 122'
                            }
                        ]
                    }
                ]
            },
            { 
                id: '2', 
                username : 'Account 2', 
                projects : [
                    { 
                        id: '21', 
                        title: 'Project 21',
                        scenarios: [
                            {
                                id: '211',
                                title: 'Scenario 211'
                            },
                            {
                                id: '212',
                                title: 'Scenario 212'
                            }
                        ]
                    }
                ]
            }

        ];

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
                        .text(entity.title || entity.username);
                if(selectedValues && _.contains(selectedValues, entity.id)){
                    $option.attr('selected', 'selected');
                }
                $select.append($option);
            });
        };

        $(function() {
            aw.$selectAccount = $('select#account');
            aw.$selectProject = $('select#project');
            aw.$selectScenario = $('select#scenario');
            aw.$inputSelectedScenarios = $('input#selectedScenarios');

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
            });

            aw.$selectProject.change(function(){
                var selectedProject = '';
                $('option:selected', aw.$selectProject).each(function() {
                  selectedProject = $( this ).val();
                  return false;
                });
                aw.selectedProject = selectedProject;
                
                aw.populateSelect(aw.$selectScenario, aw.findScenariosByAccountIdAndProjectId(aw.selectedAccount, aw.selectedProject), aw.selectedScenarios);
            });


            aw.populateSelect(aw.$selectAccount, aw.accounts, aw.selectedAccount);
            aw.populateSelect(aw.$selectProject, aw.findProjectsByAccountId(aw.selectedAccount), aw.selectedProject);
            aw.populateSelect(aw.$selectScenario, aw.findScenariosByAccountIdAndProjectId(aw.selectedAccount, aw.selectedProject), aw.selectedScenarios);
        });
    })(jQuery);    
</script>

<l:settingsGroup title="Execution">
    <tr>
        <th>
            <label for="website.automate.teamcity.account">Account</label>
        </th>
        <td>
            <props:selectProperty name="account">
                <div><c:out value="${selectedValue}"/></div>
                <props:option value="1"><c:out value="account 111"/></props:option>
                <props:option value="2"><c:out value="project 2"/></props:option>
                <props:option value="3"><c:out value="project 3"/></props:option>
                <!--<c:forEach var="project" items="${awDataProvider.projects}">
                    <props:option value="${project.id}"><c:out value="${project.title}"/></props:option>
                </c:forEach>-->
            </props:selectProperty>
        </td>
    </tr>
    <tr>
        <th>
            <label for="website.automate.teamcity.project">Project</label>
        </th>
        <td>
            <props:selectProperty name="project">
                <div><c:out value="${selectedValue}"/></div>
                <props:option value="1"><c:out value="project 111"/></props:option>
                <props:option value="2"><c:out value="project 2"/></props:option>
                <props:option value="3"><c:out value="project 3"/></props:option>
                <!--<c:forEach var="project" items="${awDataProvider.projects}">
                    <props:option value="${project.id}"><c:out value="${project.title}"/></props:option>
                </c:forEach>-->
            </props:selectProperty>
        </td>
    </tr>
    <tr>
        <th>
            <label for="website.automate.teamcity.scenario">Scenarios</label>
        </th>
        <td>
            <props:selectProperty name="scenario" multiple="true">
                <div><c:out value="${selectedValue}"/></div>
                <props:option value="1"><c:out value="scenario 1"/></props:option>
                <props:option value="2"><c:out value="scenario 2"/></props:option>
                <props:option value="3"><c:out value="scenario 3"/></props:option>
                <!--<c:forEach var="scenario" items="${awDataProvider.scenarios}">
                    <props:option value="${scenario.id}"><c:out value="${scenario.title}"/></props:option>
                </c:forEach>-->
            </props:selectProperty>
            <props:hiddenProperty name="selectedScenarios" value=""/>
        </td>
    </tr>
</l:settingsGroup>
