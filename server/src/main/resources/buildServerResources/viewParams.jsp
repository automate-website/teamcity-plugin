<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="serverConfigModel" class="website.automate.teamcity.server.global.ServerConfigModel"/>

<script type="text/javascript">
(function($){
    var aw = {};

    aw.selectedAccount = $.trim("${propertiesBean.properties['website.automate.teamcity.account']}");
    aw.selectedProject = $.trim("${propertiesBean.properties['website.automate.teamcity.project']}");
    aw.selectedScenarios = $.trim("${propertiesBean.properties['website.automate.teamcity.selectedScenarios']}").split(',');

    aw.accounts = ${serverConfigModel.accountsAsJson};
    
    aw.template = _.template('<div class="parameter">{{ paramName }}: <strong>{{ paramValue }}</strong></div>', { interpolate: /\{\{(.+?)\}\}/g });
    
    $(function() {
        aw.renderParameters();
    });

    aw.findAccountByAccountId = function(accountId){
        return _.find(aw.accounts, function(account){
                return account.id === accountId;
        });
    };

    aw.findAccountNameByAccountId = function(accountId){
        var account = aw.findAccountByAccountId(accountId);
        return _.property('username')(account);
    };

    aw.findProjectByAccountIdAndProjectId = function(accountId, projectId){
        var account = aw.findAccountByAccountId(accountId);
        return _.find(_.property('projects')(account), function(project){
            return project.id === projectId;
        });
    };

    aw.findProjectNameByAccountIdAndProjectId = function(accountId, projectId){
        var project = aw.findProjectByAccountIdAndProjectId(accountId, projectId);
        return _.property('title')(project);
    };

    aw.findScenarioNamesByAccountIdAndProjectIdAndScenrioIds = function(accountId, projectId, scenarioIds){
        var project = aw.findProjectByAccountIdAndProjectId(accountId, projectId);
        return _.chain(_.property('scenarios')(project))
            .filter(function(scenario){
                return _.contains(scenarioIds, scenario.id);
            })
            .map(function(scenario){
                return scenario.name;
            })
            .join(', ')
            .value();
    };
    
    aw.renderParameters = function(){
        var $parameterPlaceholder = $('div#aw-parameterPlaceholder');
        
        $parameterPlaceholder.after(aw.template({ paramName : 'Scenarios', paramValue : aw.findScenarioNamesByAccountIdAndProjectIdAndScenrioIds(aw.selectedAccount, aw.selectedProject, aw.selectedScenarios) }));
        $parameterPlaceholder.after(aw.template({ paramName : 'Project', paramValue : aw.findProjectNameByAccountIdAndProjectId(aw.selectedAccount, aw.selectedProject) }));
        $parameterPlaceholder.after(aw.template({ paramName : 'Account', paramValue : aw.findAccountNameByAccountId(aw.selectedAccount) }));
        $parameterPlaceholder.remove();
    };
    
})(jQuery);
</script>

<div class="parameter" id="aw-parameterPlaceholder"></div>