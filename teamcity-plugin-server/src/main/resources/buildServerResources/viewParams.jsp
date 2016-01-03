<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                return scenario.title;
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