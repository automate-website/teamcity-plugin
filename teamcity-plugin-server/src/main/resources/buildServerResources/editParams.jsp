<%@ page import="website.automate.plugins.teamcity.common.AutomateWebsiteTeamCityConstants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="automateWebsiteDataProvider" class="website.automate.plugins.teamcity.server.AutomateWebsiteDataProvider"/>

<l:settingsGroup title="Authentication">
    <tr>
        <th><label for="website.automate.teamcity.username">Username: </label></th>
        <td><props:textProperty name="<%=AutomateWebsiteTeamCityConstants.PARAM_USERNAME%>" className="longField"
                                maxlength="256"/>
        </td>
    </tr>
    <tr>
        <th>
            <label for="website.automate.teamcity.password">Password</label>
        </th>
        <td><props:passwordProperty name="<%=AutomateWebsiteTeamCityConstants.PARAM_PASSWORD%>" className="longField"
                                    maxlength="256"/>
        </td>
    </tr>
</l:settingsGroup>

<l:settingsGroup title="Execution">
    <tr>
        <th>
            <label for="website.automate.teamcity.project">Project</label>
        </th>
        <td>
            <props:selectProperty name="<%=AutomateWebsiteTeamCityConstants.PARAM_PROJECT%>" multiple="true">
                <c:forEach var="project" items="${automateWebsiteDataProvider.projects}">
                    <props:option value="${project.id}"><c:out value="${project.title}"/></props:option>
                </c:forEach>
            </props:selectProperty>
        </td>
    </tr>
    <tr>
        <th>
            <label for="website.automate.teamcity.scenarios">Scenarios</label>
        </th>
        <td>
            <props:selectProperty name="<%=AutomateWebsiteTeamCityConstants.PARAM_SCENARIOS%>" multiple="true">
                <c:forEach var="scenario" items="${automateWebsiteDataProvider.scenarios}">
                    <props:option value="${scenario.id}"><c:out value="${scenario.title}"/></props:option>
                </c:forEach>
            </props:selectProperty>
        </td>
    </tr>
</l:settingsGroup>
