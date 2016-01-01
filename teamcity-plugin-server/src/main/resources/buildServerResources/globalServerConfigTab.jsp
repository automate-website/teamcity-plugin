<%@include file="/include.jsp" %>

<jsp:useBean id="serverConfigPersistenceManager" type="website.automate.plugins.teamcity.server.global.ServerConfigPersistenceManager"
             scope="request"/>

<style type="text/css">
    .editObjectFormDialog label {
        display: inline-block;
        width: 14.5em;
    }
</style>

<bs:linkCSS>
    /css/admin/vcsSettings.css
</bs:linkCSS>

<c:url var="controllerUrl" value="/admin/automate-website/globalServerConfigTab.html"/>

<script type="text/javascript">

    var AccountSyncDialog = OO.extend(BS.AbstractModalDialog, {
        getContainer: function() {
            return $('AccountSyncDialog');
        },

        showAccountSyncDialog: function(successful, syncDetails) {
            if (successful) {
                $('syncStatus').innerHTML = 'Account sync successful!';
                $('syncStatus').className = 'accountSyncSuccess';
            } else {
                $('syncStatus').innerHTML = 'Account sync failed!';
                $('syncStatus').className = 'accountSyncFailed';
            }
            $('accountSyncDetails').innerHTML = syncDetails;

            $('accountSyncDetails').style.height = '';
            $('accountSyncDetails').style.overflow = 'auto';
            this.showCentered();
        }
    });

    var ConfigTabDialog = OO.extend(BS.AbstractPasswordForm,
            OO.extend(BS.AbstractModalDialog, { // will copy all properties from AbstractModalDialog and AbstractWebForm
                getContainer: function() {
                    return $('editObjectFormDialog');
                },

                formElement: function() {
                    return $('editObjectForm');
                },

                savingIndicator: function() {
                    return $('saving_configTab');
                },

                showAddDialog: function() {
                    ConfigTabDialog.enable();
                    this.formElement().id.value = '';
                    this.formElement().username.value = '';
                    this.formElement().password.value = '';
                    $('errorUsername').innerHTML = '';
                    $('errorPassword').innerHTML = '';
                    this.formElement().editMode.value = 'add';
                    this.showCentered();
                },

                showEditDialog: function(id, username, password) {
                    ConfigTabDialog.enable();
                    this.formElement().id.value = id;
                    this.formElement().username.value = username;
                    this.formElement().password.value = password;
                    $('errorUsername').innerHTML = '';
                    $('errorPassword').innerHTML = '';
                    this.formElement().editMode.value = 'edit';
                    this.showCentered();
                },

                isValueNotBlank: function(valueToCheck) {
                    return (valueToCheck != null) && (valueToCheck.length != 0);
                },

                save: function() {
                    var that = this;

                    // will serialize form params, and submit form to form.action
                    // if XML with errors is returned, corresponding error listener methods will be called
                    BS.PasswordFormSaver.save(this, this.formElement().action, OO.extend(BS.ErrorsAwareListener, {
                    	errorUsername : function(elem) {
                            $('errorUsername').innerHTML = elem.firstChild.nodeValue;
                        },
                        errorPassword : function(elem) {
                            $('errorPassword').innerHTML = elem.firstChild.nodeValue;
                        },
                        onSuccessfulSave: function() {
                            ConfigTabDialog.enable();
                            ConfigTabDialog.close();
                            $('objectsTable').refresh();
                        }
                    }), false);

                    return false;
                },

                syncAccount: function() {
                    var that = this;

                    // will serialize form params, and submit form to form.action
                    // if XML with errors is returned, corresponding error listener methods will be called
                    BS.PasswordFormSaver.save(this, this.formElement().action + '?sync=true',
                            OO.extend(BS.ErrorsAwareListener, {
                            	errorUsername : function(elem) {
                                    $('errorUsername').innerHTML = elem.firstChild.nodeValue;
                                },
                                errorPassword : function(elem) {
                                    $('errorPassword').innerHTML = elem.firstChild.nodeValue;
                                },
                                errorConnection : function(elem) {
                                    AccountSyncDialog.showAccountSyncDialog(false, elem.firstChild.nodeValue);
                                },
                                onSuccessfulSave: function() {
                                    AccountSyncDialog.showAccountSyncDialog(true,
                                            'Connection was successfully established.');
                                }
                            }), false);

                    return false;
                },

                deleteObject: function(id) {
                    if (!confirm('Are you sure you wish to delete this configuration?')) return false;

                    BS.ajaxRequest('${controllerUrl}', {
                        parameters: 'deleteObject=' + id,
                        onComplete: function() {
                            $('objectsTable').refresh();
                        }
                    })
                }
            }));
</script>
<div>

    <bs:refreshable containerId="objectsTable" pageUrl="${pageUrl}">

        <table border="0" style="width: 80%;">
            <bs:messages key="objectUpdated"/>
            <bs:messages key="objectCreated"/>
            <bs:messages key="objectDeleted"/>
        </table>

        <l:tableWithHighlighting className="settings" highlightImmediately="true" style="width: 80%;">
            <tr>
                <th>Automate Website Account</th>
                <th colspan="2">Actions</th>
            </tr>
            <c:forEach var="account" items="${serverConfigPersistenceManager.accounts}">
                <c:set var="onclick">
                    ConfigTabDialog.showEditDialog('${account.id}', 
                    '${account.username}',
                    '${account.password}');
                </c:set>
                <tr>
                    <td class="highlight" onclick="${onclick}">
                        <c:out value="${account.username}"/>
                    </td>
                    <td class="edit highlight">
                        <a href="#" onclick="${onclick} return false">sync</a>
                    </td>
                    <td class="edit highlight">
                        <a href="#" onclick="${onclick} return false">edit</a>
                    </td>
                    <td class="edit highlight">
                        <a href="#" onclick="ConfigTabDialog.deleteObject('${account.id}'); return false">delete</a>
                    </td>
                </tr>
            </c:forEach>
        </l:tableWithHighlighting>
    </bs:refreshable>

    <p>
        <a class="btn" href="#" onclick="ConfigTabDialog.showAddDialog(); return false">
            <span class="addNew">Create new Automate Website account configuration</span>
        </a>
    </p>

    <bs:modalDialog formId="editObjectForm" title="Edit Automate Website Account Configuration" action="${controllerUrl}"
                    saveCommand="ConfigTabDialog.save();" closeCommand="ConfigTabDialog.close();">

        <table border="0" style="width: 409px">
            <tr>
                <td>
                    <label for="url">Username:
                        <span class="mandatoryAsterix" title="Mandatory field">*</span>
                        <bs:helpIcon
                                iconTitle="Specify the account username."/>
                    </label>
                </td>
                <td>
                    <forms:textField name="username" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span class="error" id="errorUsername" style="margin-left: 0;"></span>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="password">Password:
                        <span class="mandatoryAsterix" title="Mandatory field">*</span>
                        <bs:helpIcon
                                iconTitle="Specify the account password."/>
                    </label>
                </td>
                <td>
                    <forms:passwordField name="password"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span class="error" id="errorPassword" style="margin-left: 0;"></span>
                </td>
            </tr>
        </table>
        
        <div class="saveButtonsBlock">
            <a href="#" onclick="ConfigTabDialog.close(); return false" class="btn cancel">Cancel</a>
            <input class="btn btn_primary submitButton" type="submit" name="editObject" value="Save">
            <input type="hidden" name="id" value="">
            <input type="hidden" name="editMode" value="">
            <input type="hidden" id="publicKey" name="publicKey"
                   value="qwe"/>
            <forms:saving id="saving_configTab"/>
        </div>

        <bs:dialog dialogId="AccountSyncDialog" dialogClass="vcsRootAccountSyncDialog" title="Account Sync"
                   closeCommand="BS.AccountSyncDialog.close(); ConfigTabDialog.enable();"
                   closeAttrs="showdiscardchangesmessage='false'">
            <div id="syncStatus"></div>
            <div id="accountSyncDetails"></div>
        </bs:dialog>
    </bs:modalDialog>
</div>