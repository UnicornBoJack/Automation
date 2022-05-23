package com.jens.automation2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

public class ActivityManageTriggerBroadcast extends Activity
{
    RadioButton rbBroadcastReceived, rbBroadcastNotReceived;
    EditText etBroadcastTriggerAction;
    Button bBroadcastShowSuggestions, bSaveTriggerBroadcast;
    TextView tvBroadcastUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_trigger_broadcasts);

        bBroadcastShowSuggestions = findViewById(R.id.bBroadcastShowSuggestions);
        bSaveTriggerBroadcast = findViewById(R.id.bSaveTriggerBroadcast);
        etBroadcastTriggerAction = findViewById(R.id.etBroadcastTriggerAction);
        rbBroadcastReceived = findViewById(R.id.rbBroadcastReceived);
        rbBroadcastNotReceived = findViewById(R.id.rbBroadcastNotReceived);
        tvBroadcastUrl = findViewById(R.id.tvBroadcastUrl);

        if(getIntent().hasExtra(ActivityManageRule.intentNameTriggerParameter1) && getIntent().hasExtra(ActivityManageRule.intentNameTriggerParameter2))
        {
            if(getIntent().getBooleanExtra(ActivityManageRule.intentNameTriggerParameter1, true))
                rbBroadcastReceived.setChecked(true);
            else
                rbBroadcastNotReceived.setChecked(true);

            etBroadcastTriggerAction.setText(getIntent().getStringExtra(ActivityManageRule.intentNameTriggerParameter2));
        }

        tvBroadcastUrl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.broadcastListUrl)));
                startActivity(browserIntent);
            }
        });

        bBroadcastShowSuggestions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityManageTriggerBroadcast.this);
                builder.setTitle(getResources().getString(R.string.selectBroadcast));
                builder.setItems(broadcastSuggestions, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        etBroadcastTriggerAction.setText(broadcastSuggestions[which]);
                    }
                });
                builder.create().show();
            }
        });

        bSaveTriggerBroadcast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(etBroadcastTriggerAction.getText() != null && !StringUtils.isEmpty(etBroadcastTriggerAction.getText().toString()))
                {
                    Intent data = new Intent();
                    data.putExtra(ActivityManageRule.intentNameTriggerParameter1, rbBroadcastReceived.isChecked());
                    data.putExtra(ActivityManageRule.intentNameTriggerParameter2, etBroadcastTriggerAction.getText().toString());
                    ActivityManageTriggerBroadcast.this.setResult(RESULT_OK, data);

                    finish();
                }
                else
                    Toast.makeText(ActivityManageTriggerBroadcast.this, getResources().getString(R.string.enterText), Toast.LENGTH_SHORT).show();
            }
        });
    }

    String[] broadcastSuggestions = {
            "android.accounts.LOGIN_ACCOUNTS_CHANGED",
            "android.accounts.action.ACCOUNT_REMOVED",
            "android.app.action.ACTION_PASSWORD_CHANGED",
            "android.app.action.ACTION_PASSWORD_EXPIRING",
            "android.app.action.ACTION_PASSWORD_FAILED",
            "android.app.action.ACTION_PASSWORD_SUCCEEDED",
            "android.app.action.AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE",
            "android.app.action.APPLICATION_DELEGATION_SCOPES_CHANGED",
            "android.app.action.APP_BLOCK_STATE_CHANGED",
            "android.app.action.AUTOMATIC_ZEN_RULE_STATUS_CHANGED",
            "android.app.action.BUGREPORT_FAILED",
            "android.app.action.BUGREPORT_SHARE",
            "android.app.action.BUGREPORT_SHARING_DECLINED",
            "android.app.action.DATA_SHARING_RESTRICTION_APPLIED",
            "android.app.action.DATA_SHARING_RESTRICTION_CHANGED",
            "android.app.action.DEVICE_ADMIN_DISABLED",
            "android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED",
            "android.app.action.DEVICE_ADMIN_ENABLED",
            "android.app.action.DEVICE_OWNER_CHANGED",
            "android.app.action.INTERRUPTION_FILTER_CHANGED",
            "android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL",
            "android.app.action.LOCK_TASK_ENTERING",
            "android.app.action.LOCK_TASK_EXITING",
            "android.app.action.MANAGED_USER_CREATED",
            "android.app.action.NETWORK_LOGS_AVAILABLE",
            "android.app.action.NEXT_ALARM_CLOCK_CHANGED",
            "android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED",
            "android.app.action.NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED",
            "android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED",
            "android.app.action.NOTIFICATION_POLICY_CHANGED",
            "android.app.action.NOTIFY_PENDING_SYSTEM_UPDATE",
            "android.app.action.PROFILE_OWNER_CHANGED",
            "android.app.action.PROFILE_PROVISIONING_COMPLETE",
            "android.app.action.SECURITY_LOGS_AVAILABLE",
            "android.app.action.SYSTEM_UPDATE_POLICY_CHANGED",
            "android.app.action.TRANSFER_OWNERSHIP_COMPLETE",
            "android.app.action.USER_ADDED",
            "android.app.action.USER_REMOVED",
            "android.app.action.USER_STARTED",
            "android.app.action.USER_STOPPED",
            "android.app.action.USER_SWITCHED",
            "android.appwidget.action.APPWIDGET_DELETED",
            "android.appwidget.action.APPWIDGET_DISABLED",
            "android.appwidget.action.APPWIDGET_ENABLED",
            "android.appwidget.action.APPWIDGET_HOST_RESTORED",
            "android.appwidget.action.APPWIDGET_RESTORED",
            "android.appwidget.action.APPWIDGET_UPDATE",
            "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS",
            "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED",
            "android.bluetooth.a2dp.profile.action.AVRCP_CONNECTION_STATE_CHANGED",
            "android.bluetooth.a2dp.profile.action.CODEC_CONFIG_CHANGED",
            "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED",
            "android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.adapter.action.DISCOVERY_FINISHED",
            "android.bluetooth.adapter.action.DISCOVERY_STARTED",
            "android.bluetooth.adapter.action.LOCAL_NAME_CHANGED",
            "android.bluetooth.adapter.action.SCAN_MODE_CHANGED",
            "android.bluetooth.adapter.action.STATE_CHANGED",
            "android.bluetooth.device.action.ACL_CONNECTED",
            "android.bluetooth.device.action.ACL_DISCONNECTED",
            "android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED",
            "android.bluetooth.device.action.ALIAS_CHANGED",
            "android.bluetooth.device.action.BATTERY_LEVEL_CHANGED",
            "android.bluetooth.device.action.BOND_STATE_CHANGED",
            "android.bluetooth.device.action.CLASS_CHANGED",
            "android.bluetooth.device.action.CONNECTION_ACCESS_CANCEL",
            "android.bluetooth.device.action.CONNECTION_ACCESS_REPLY",
            "android.bluetooth.device.action.CONNECTION_ACCESS_REQUEST",
            "android.bluetooth.device.action.FOUND",
            "android.bluetooth.device.action.MAS_INSTANCE",
            "android.bluetooth.device.action.NAME_CHANGED",
            "android.bluetooth.device.action.NAME_FAILED",
            "android.bluetooth.device.action.PAIRING_CANCEL",
            "android.bluetooth.device.action.PAIRING_REQUEST",
            "android.bluetooth.device.action.SDP_RECORD",
            "android.bluetooth.device.action.SILENCE_MODE_CHANGED",
            "android.bluetooth.device.action.UUID",
            "android.bluetooth.devicepicker.action.DEVICE_SELECTED",
            "android.bluetooth.devicepicker.action.LAUNCH",
            "android.bluetooth.headset.action.VENDOR_SPECIFIC_HEADSET_EVENT",
            "android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED",
            "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED",
            "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED",
            "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.hiddevice.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.input.profile.action.HANDSHAKE",
            "android.bluetooth.input.profile.action.IDLE_TIME_CHANGED",
            "android.bluetooth.input.profile.action.PROTOCOL_MODE_CHANGED",
            "android.bluetooth.input.profile.action.REPORT",
            "android.bluetooth.input.profile.action.VIRTUAL_UNPLUG_STATUS",
            "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.pbap.profile.action.CONNECTION_STATE_CHANGED",
            "android.content.pm.action.SESSION_COMMITTED",
            "android.content.pm.action.SESSION_UPDATED",
            "android.hardware.action.NEW_PICTURE",
            "android.hardware.action.NEW_VIDEO",
            "android.hardware.hdmi.action.OSD_MESSAGE",
            "android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS",
            "android.hardware.usb.action.USB_ACCESSORY_ATTACHED",
            "android.hardware.usb.action.USB_ACCESSORY_DETACHED",
            "android.hardware.usb.action.USB_DEVICE_ATTACHED",
            "android.hardware.usb.action.USB_DEVICE_DETACHED",
            "android.intent.action.ACTION_IDLE_MAINTENANCE_END",
            "android.intent.action.ACTION_IDLE_MAINTENANCE_START",
            "android.intent.action.ACTION_POWER_CONNECTED",
            "android.intent.action.ACTION_POWER_DISCONNECTED",
            "android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED",
            "android.intent.action.ACTION_SHUTDOWN",
            "android.intent.action.AIRPLANE_MODE",
            "android.intent.action.ALARM_CHANGED",
            "android.intent.action.APPLICATION_RESTRICTIONS_CHANGED",
            "android.intent.action.BATTERY_CHANGED",
            "android.intent.action.BATTERY_LOW",
            "android.intent.action.BATTERY_OKAY",
            "android.intent.action.BOOT_COMPLETED",
            "android.intent.action.CALL_DISCONNECT_CAUSE",
            "android.intent.action.CAMERA_BUTTON",
            "android.intent.action.CANCEL_ENABLE_ROLLBACK",
            "android.intent.action.CLEAR_DNS_CACHE",
            "android.intent.action.CLOSE_SYSTEM_DIALOGS",
            "android.intent.action.CONFIGURATION_CHANGED",
            "android.intent.action.CONTENT_CHANGED",
            "android.intent.action.DATA_SMS_RECEIVED",
            "android.intent.action.DATA_STALL_DETECTED",
            "android.intent.action.DATE_CHANGED",
            "android.intent.action.DEVICE_STORAGE_FULL",
            "android.intent.action.DEVICE_STORAGE_LOW",
            "android.intent.action.DEVICE_STORAGE_NOT_FULL",
            "android.intent.action.DEVICE_STORAGE_OK",
            "android.intent.action.DISTRACTING_PACKAGES_CHANGED",
            "android.intent.action.DOCK_EVENT",
            "android.intent.action.DOWNLOAD_COMPLETE",
            "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED",
            "android.intent.action.DREAMING_STARTED",
            "android.intent.action.DREAMING_STOPPED",
            "android.intent.action.DROPBOX_ENTRY_ADDED",
            "android.intent.action.DYNAMIC_SENSOR_CHANGED",
            "android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED",
            "android.intent.action.EMERGENCY_CALL_STATE_CHANGED",
            "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE",
            "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE",
            "android.intent.action.FACTORY_RESET",
            "android.intent.action.FETCH_VOICEMAIL",
            "android.intent.action.GTALK_CONNECTED",
            "android.intent.action.GTALK_DISCONNECTED",
            "android.intent.action.HEADSET_PLUG",
            "android.intent.action.HEADSET_PLUG",
            "android.intent.action.INPUT_METHOD_CHANGED",
            "android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION",
            "android.intent.action.LOCALE_CHANGED",
            "android.intent.action.LOCKED_BOOT_COMPLETED",
            "android.intent.action.MANAGE_PACKAGE_STORAGE",
            "android.intent.action.MASTER_CLEAR_NOTIFICATION",
            "android.intent.action.MEDIA_BAD_REMOVAL",
            "android.intent.action.MEDIA_BUTTON",
            "android.intent.action.MEDIA_CHECKING",
            "android.intent.action.MEDIA_EJECT",
            "android.intent.action.MEDIA_MOUNTED",
            "android.intent.action.MEDIA_NOFS",
            "android.intent.action.MEDIA_REMOVED",
            "android.intent.action.MEDIA_SCANNER_FINISHED",
            "android.intent.action.MEDIA_SCANNER_SCAN_FILE",
            "android.intent.action.MEDIA_SCANNER_STARTED",
            "android.intent.action.MEDIA_SHARED",
            "android.intent.action.MEDIA_UNMOUNTABLE",
            "android.intent.action.MEDIA_UNMOUNTED",
            "android.intent.action.MY_PACKAGE_REPLACED",
            "android.intent.action.MY_PACKAGE_SUSPENDED",
            "android.intent.action.MY_PACKAGE_UNSUSPENDED",
            "android.intent.action.NEW_OUTGOING_CALL",
            "android.intent.action.NEW_VOICEMAIL",
            "android.intent.action.PACKAGES_SUSPENDED",
            "android.intent.action.PACKAGES_UNSUSPENDED",
            "android.intent.action.PACKAGE_ADDED",
            "android.intent.action.PACKAGE_CHANGED",
            "android.intent.action.PACKAGE_DATA_CLEARED",
            "android.intent.action.PACKAGE_ENABLE_ROLLBACK",
            "android.intent.action.PACKAGE_FIRST_LAUNCH",
            "android.intent.action.PACKAGE_FULLY_REMOVED",
            "android.intent.action.PACKAGE_INSTALL",
            "android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION",
            "android.intent.action.PACKAGE_NEEDS_VERIFICATION",
            "android.intent.action.PACKAGE_REMOVED",
            "android.intent.action.PACKAGE_REPLACED",
            "android.intent.action.PACKAGE_RESTARTED",
            "android.intent.action.PACKAGE_UNSUSPENDED_MANUALLY",
            "android.intent.action.PACKAGE_VERIFIED",
            "android.intent.action.PHONE_STATE",
            "android.intent.action.PROVIDER_CHANGED",
            "android.intent.action.PROXY_CHANGE",
            "android.intent.action.QUERY_PACKAGE_RESTART",
            "android.intent.action.REBOOT",
            "android.intent.action.ROLLBACK_COMMITTED",
            "android.intent.action.SCREEN_OFF",
            "android.intent.action.SCREEN_ON",
            "android.intent.action.SERVICE_STATE",
            "android.intent.action.SIM_STATE_CHANGED",
            "android.intent.action.SPLIT_CONFIGURATION_CHANGED",
            "android.intent.action.SUB_DEFAULT_CHANGED",
            "android.intent.action.TIMEZONE_CHANGED",
            "android.intent.action.TIME_SET",
            "android.intent.action.TIME_TICK",
            "android.intent.action.UID_REMOVED",
            "android.intent.action.UMS_CONNECTED",
            "android.intent.action.UMS_DISCONNECTED",
            "android.intent.action.USER_PRESENT",
            "android.intent.action.USER_UNLOCKED",
            "android.intent.action.WALLPAPER_CHANGED",
            "android.media.ACTION_SCO_AUDIO_STATE_UPDATED",
            "android.media.AUDIO_BECOMING_NOISY",
            "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION",
            "android.media.MASTER_MUTE_CHANGED_ACTION",
            "android.media.RINGER_MODE_CHANGED",
            "android.media.SCO_AUDIO_STATE_CHANGED",
            "android.media.STREAM_DEVICES_CHANGED_ACTION",
            "android.media.STREAM_MUTE_CHANGED_ACTION",
            "android.media.VIBRATE_SETTING_CHANGED",
            "android.media.VOLUME_CHANGED_ACTION",
            "android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION",
            "android.media.action.HDMI_AUDIO_PLUG",
            "android.media.action.MICROPHONE_MUTE_CHANGED",
            "android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION",
            "android.media.action.SPEAKERPHONE_STATE_CHANGED",
            "android.media.tv.action.CHANNEL_BROWSABLE_REQUESTED",
            "android.media.tv.action.INITIALIZE_PROGRAMS",
            "android.media.tv.action.PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT",
            "android.media.tv.action.PREVIEW_PROGRAM_BROWSABLE_DISABLED",
            "android.media.tv.action.WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED",
            "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED",
            "android.net.conn.CAPTIVE_PORTAL_TEST_COMPLETED",
            "android.net.conn.CONNECTIVITY_CHANGE",
            "android.net.conn.DATA_ACTIVITY_CHANGE",
            "android.net.conn.INET_CONDITION_ACTION",
            "android.net.conn.RESTRICT_BACKGROUND_CHANGED",
            "android.net.conn.TETHER_STATE_CHANGED",
            "android.net.nsd.STATE_CHANGED",
            "android.net.scoring.SCORER_CHANGED",
            "android.net.scoring.SCORE_NETWORKS",
            "android.net.sip.action.SIP_CALL_OPTION_CHANGED",
            "android.net.sip.action.SIP_INCOMING_CALL",
            "android.net.sip.action.SIP_REMOVE_PROFILE",
            "android.net.sip.action.SIP_SERVICE_UP",
            "android.net.sip.action.START_SIP",
            "android.net.wifi.BATCHED_RESULTS",
            "android.net.wifi.NETWORK_IDS_CHANGED",
            "android.net.wifi.RSSI_CHANGED",
            "android.net.wifi.SCAN_RESULTS",
            "android.net.wifi.STATE_CHANGE",
            "android.net.wifi.WIFI_STATE_CHANGED",
            "android.net.wifi.action.WIFI_NETWORK_SUGGESTION_POST_CONNECTION",
            "android.net.wifi.action.WIFI_SCAN_AVAILABILITY_CHANGED",
            "android.net.wifi.aware.action.WIFI_AWARE_STATE_CHANGED",
            "android.net.wifi.p2p.CONNECTION_STATE_CHANGE",
            "android.net.wifi.p2p.DISCOVERY_STATE_CHANGE",
            "android.net.wifi.p2p.PEERS_CHANGED",
            "android.net.wifi.p2p.STATE_CHANGED",
            "android.net.wifi.p2p.THIS_DEVICE_CHANGED",
            "android.net.wifi.rtt.action.WIFI_RTT_STATE_CHANGED",
            "android.net.wifi.supplicant.CONNECTION_CHANGE",
            "android.net.wifi.supplicant.STATE_CHANGE",
            "android.nfc.action.ADAPTER_STATE_CHANGED",
            "android.nfc.action.PREFERRED_PAYMENT_CHANGED",
            "android.nfc.action.TRANSACTION_DETECTED",
            "android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED",
            "android.os.action.DEVICE_IDLE_MODE_CHANGED",
            "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED",
            "android.os.action.POWER_SAVE_MODE_CHANGED",
            "android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL",
            "android.os.action.POWER_SAVE_MODE_CHANGING",
            "android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED",
            "android.os.action.POWER_SAVE_WHITELIST_CHANGED",
            "android.os.action.UPDATE_EMERGENCY_NUMBER_DB",
            "android.provider.Telephony.MMS_DOWNLOADED",
            "android.provider.Telephony.SECRET_CODE",
            "android.provider.Telephony.SIM_FULL",
            "android.provider.Telephony.SMS_CARRIER_PROVISION",
            "android.provider.Telephony.SMS_CB_RECEIVED",
            "android.provider.Telephony.SMS_DELIVER",
            "android.provider.Telephony.SMS_RECEIVED",
            "android.provider.Telephony.SMS_REJECTED",
            "android.provider.Telephony.SMS_SERVICE_CATEGORY_PROGRAM_DATA_RECEIVED",
            "android.provider.Telephony.WAP_PUSH_DELIVER",
            "android.provider.Telephony.WAP_PUSH_RECEIVED",
            "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED",
            "android.provider.action.EXTERNAL_PROVIDER_CHANGE",
            "android.provider.action.SMS_EMERGENCY_CB_RECEIVED",
            "android.provider.action.SMS_MMS_DB_CREATED",
            "android.provider.action.SMS_MMS_DB_LOST",
            "android.provider.action.SYNC_VOICEMAIL",
            "android.security.STORAGE_CHANGED",
            "android.security.action.KEYCHAIN_CHANGED",
            "android.security.action.KEY_ACCESS_CHANGED",
            "android.security.action.TRUST_STORE_CHANGED",
            "android.service.controls.action.ADD_CONTROL",
            "android.settings.ENABLE_MMS_DATA_REQUEST",
            "android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED",
            "android.speech.tts.engine.TTS_DATA_INSTALLED",
            "android.telephony.action.AREA_INFO_UPDATED",
            "android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED",
            "android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED",
            "android.telephony.action.PRIMARY_SUBSCRIPTION_LIST_CHANGED",
            "android.telephony.action.REFRESH_SUBSCRIPTION_PLANS",
            "android.telephony.action.SECRET_CODE",
            "android.telephony.action.SERVICE_PROVIDERS_UPDATED",
            "android.telephony.action.SIM_APPLICATION_STATE_CHANGED",
            "android.telephony.action.SIM_CARD_STATE_CHANGED",
            "android.telephony.action.SIM_SLOT_STATUS_CHANGED",
            "android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED",
            "android.telephony.action.SUBSCRIPTION_PLANS_CHANGED",
            "android.telephony.action.SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED",
            "android.telephony.euicc.action.NOTIFY_CARRIER_SETUP_INCOMPLETE",
            "android.telephony.euicc.action.OTA_STATUS_CHANGED",
            "android.telephony.ims.action.WFC_IMS_REGISTRATION_ERROR",
            "com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS",
            "com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS",
            "com.android.internal.intent.action.ACTION_FORBIDDEN_NO_SERVICE_AUTHORIZATION",
            "com.android.internal.provider.action.VOICEMAIL_SMS_RECEIVED"
    };
}