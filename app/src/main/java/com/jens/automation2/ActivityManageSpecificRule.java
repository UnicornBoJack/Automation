package com.jens.automation2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jens.automation2.Action.Action_Enum;
import com.jens.automation2.Trigger.Trigger_Enum;
import com.jens.automation2.receivers.ActivityDetectionReceiver;
import com.jens.automation2.receivers.NfcReceiver;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityManageSpecificRule extends Activity
{
	public Context context;
	private Button cmdTriggerAdd, cmdActionAdd, cmdSaveRule;
	private ListView triggerListView, actionListView;
	private EditText etRuleName;
	private CheckBox chkRuleActive, chkRuleToggle;
	private static ActivityManageSpecificRule instance = null;
	ImageView imageHelpButton;
	
	private static ProgressDialog progressDialog = null;
	
	private static Trigger_Enum triggerType;
	private static boolean triggerParameter;
	private static PointOfInterest triggerPoi;
	private static String triggerProcess;
	private static int triggerBattery;
	private static double triggerSpeed;
	private static double triggerNoise;
	private static TimeFrame triggerTimeFrame;
	private static String triggerWifiName;
	
	private static Rule ruleToEdit;
	private static boolean newRule;
	
	private static Trigger newTrigger;
	private static Action newAction;

	ArrayAdapter<Trigger> triggerListViewAdapter;
	ArrayAdapter<Action> actionListViewAdapter;

	int editIndex = 0;

	final static int requestCodeActionTriggerUrlAdd = 1000;
	final static int requestCodeActionTriggerUrlEdit = 1001;
	final static int requestCodeTriggerTimeframeAdd = 2000;
	final static int requestCodeTriggerTimeframeEdit = 2001;
	final static int requestCodeActionStartActivityAdd = 3000;
	final static int requestCodeActionStartActivityEdit = 3001;
	final static int requestCodeTriggerNfcTagAdd = 4000;
	final static int requestCodeTriggerNfcTagEdit = 4001;
	final static int requestCodeActionSpeakTextAdd = 5000;
	final static int requestCodeActionSpeakTextEdit = 1001;
	final static int requestCodeTriggerBluetoothAdd = 6000;
	final static int requestCodeTriggerBluetoothEdit = 6001;
	final static int requestCodeActionScreenBrightnessAdd = 401;
	final static int requestCodeActionScreenBrightnessEdit = 402;
	final static int requestCodeActionSendTextMessage = 7001;
	
	public static ActivityManageSpecificRule getInstance()
	{
		if(instance == null)
			instance = new ActivityManageSpecificRule();
		
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_specific_rule);
		
		instance = this;
		
		cmdTriggerAdd = (Button)findViewById(R.id.cmdTriggerAdd);
		cmdActionAdd = (Button)findViewById(R.id.cmdActionAdd);
		triggerListView = (ListView)findViewById(R.id.lvTriggerListView);
		actionListView = (ListView)findViewById(R.id.lvActionListView);
		etRuleName = (EditText)findViewById(R.id.etRuleName);
		cmdSaveRule = (Button)findViewById(R.id.cmdSaveRule);
		chkRuleActive = (CheckBox)findViewById(R.id.chkRuleActive);
		chkRuleToggle = (CheckBox)findViewById(R.id.chkRuleToggle);
		imageHelpButton = (ImageView)findViewById(R.id.imageHelpButton);
		
		//decide if it will be created anew or loaded to edit an existing one
		if(ActivityMainRules.ruleToEdit == null)
		{
			// new rule
			Miscellaneous.logEvent("i", "Rule", "Cache empty, assuming create request.", 3);
			newRule = true;
			ruleToEdit = new Rule();
			ruleToEdit.setTriggerSet(new ArrayList<Trigger>());
			ruleToEdit.setActionSet(new ArrayList<Action>());
			triggerListViewAdapter = new ArrayAdapter<Trigger>(this, R.layout.text_view_for_poi_listview_mediumtextsize, ruleToEdit.getTriggerSet());
			actionListViewAdapter = new ArrayAdapter<Action>(this, R.layout.text_view_for_poi_listview_mediumtextsize, ruleToEdit.getActionSet());
		}
		else
		{
			// change existing rule
			Miscellaneous.logEvent("i", "Rule", "Cache not empty, assuming change request.", 3);
			newRule = false;
			ruleToEdit = ActivityMainRules.ruleToEdit;
			triggerListViewAdapter = new ArrayAdapter<Trigger>(this, R.layout.text_view_for_poi_listview_mediumtextsize, ruleToEdit.getTriggerSet());
			actionListViewAdapter = new ArrayAdapter<Action>(this, R.layout.text_view_for_poi_listview_mediumtextsize, ruleToEdit.getActionSet());
			loadVariablesIntoGui();
		}
		
		cmdTriggerAdd.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				hideKeyboard();
				newTrigger = new Trigger();
				getTriggerTypeDialog(context).show();
			}
		});
		
		cmdActionAdd.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				hideKeyboard();
				getActionTypeDialog().show();
			}
		});		
		
		cmdSaveRule.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				if(newRule)
				{
					Miscellaneous.logEvent("i", "Rule", "Will create a new rule.", 3);
					loadFormValuesToVariable();
					if(ruleToEdit.create(context))
					{
						ActivityPermissions.getRequiredPermissions(false);
						finish();
					}
					else
						Toast.makeText(ActivityManageSpecificRule.this, getResources().getString(R.string.errorWritingConfig), Toast.LENGTH_LONG).show();
				}
				else
				{
					Miscellaneous.logEvent("i", "Rule", "Will change an existing rule.", 3);
					loadFormValuesToVariable();
					if(ruleToEdit.change(context))
					{
						ActivityPermissions.getRequiredPermissions(false);
						finish();
					}
					else
						Toast.makeText(ActivityManageSpecificRule.this, getResources().getString(R.string.errorWritingConfig), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		triggerListView.setClickable(true);
		actionListView.setClickable(true);
		
		triggerListView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				getTriggerDeleteDialog(context, (Trigger)triggerListView.getItemAtPosition(arg2)).show();
				return false;
			}
		});
		triggerListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				editIndex = arg2;
				Trigger selectedTrigger = (Trigger)triggerListView.getItemAtPosition(arg2);
				switch(selectedTrigger.getTriggerType())
				{
//					case batteryLevel:
//						break;
//					case charging:
//						break;
//					case noiseLevel:
//						break;
//					case pointOfInterest:
//						break;
//					case process_started_stopped:
//						break;
//					case speed:
//						break;
					case timeFrame:
						ActivityManageTimeFrame.editedTimeFrameTrigger = selectedTrigger;
						Intent timeFrameEditor = new Intent(ActivityManageSpecificRule.this, ActivityManageTimeFrame.class);
						startActivityForResult(timeFrameEditor, requestCodeTriggerTimeframeEdit);
						break;
//					case usb_host_connection:
//						break;
//					case wifiConnection:
//						break;
					case bluetoothConnection:
						ActivityManageBluetoothTrigger.editedBluetoothTrigger = selectedTrigger;
						Intent bluetoothEditor = new Intent(ActivityManageSpecificRule.this, ActivityManageBluetoothTrigger.class);
						startActivityForResult(bluetoothEditor, requestCodeTriggerBluetoothEdit);
						break;
					default:
						break;				
				}
			}
		});
		triggerListView.setOnTouchListener(new OnTouchListener()
		{			
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		
		actionListView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				getActionDeleteDialog(context, (Action)actionListView.getItemAtPosition(arg2)).show();
				return false;
			}
		});
		actionListView.setOnItemClickListener(new OnItemClickListener()
		{
			// editing triggers
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				editIndex = arg2;
				Action a = (Action)actionListView.getItemAtPosition(arg2);
				switch(a.getAction())
				{
//					case changeSoundProfile:
//						break;
//					case disableScreenRotation:
//						break;
//					case enableScreenRotation:
//						break;
//					case setAirplaneMode:
//						break;
					case startOtherActivity:
						Intent intent = new Intent(ActivityManageSpecificRule.this, ActivityManageStartActivity.class);
						ActivityManageStartActivity.resultingAction = a;
						intent.putExtra("edit", true);
						startActivityForResult(intent, requestCodeActionStartActivityEdit);
						break;
					case triggerUrl:
						Intent activityEditTriggerUrlIntent = new Intent(ActivityManageSpecificRule.this, ActivityEditTriggerUrl.class);
//						activityEditTriggerUrlIntent.putExtra("urlToTrigger", a.getParameter2());
						ActivityEditTriggerUrl.resultingAction = a;
						activityEditTriggerUrlIntent.putExtra("edit", true);
						startActivityForResult(activityEditTriggerUrlIntent, requestCodeActionTriggerUrlEdit);
						break;
//					case turnBluetoothOff:
//						break;
//					case turnBluetoothOn:
//						break;
//					case turnUsbTetheringOff:
//						break;
//					case turnUsbTetheringOn:
//						break;
//					case turnWifiOff:
//						break;
//					case turnWifiOn:
//						break;
//					case turnWifiTetheringOff:
//						break;
//					case turnWifiTetheringOn:
//						break;
//					case waitBeforeNextAction:
//						break;
//					case wakeupDevice:
//						break;
					case speakText:
						Intent activitySpeakTextIntent = new Intent(ActivityManageSpecificRule.this, ActivityEditSpeakText.class);
						ActivityEditSpeakText.resultingAction = a;
						activitySpeakTextIntent.putExtra("edit", true);
						startActivityForResult(activitySpeakTextIntent, requestCodeActionSpeakTextEdit);
						break;
					case sendTextMessage:
						Intent activitySendTextMessageIntent = new Intent(ActivityManageSpecificRule.this, ActivityEditSendTextMessage.class);
						ActivityEditSendTextMessage.resultingAction = a;
						activitySendTextMessageIntent.putExtra("edit", true);
						startActivityForResult(activitySendTextMessageIntent, requestCodeActionSendTextMessage);
						break;
					case setScreenBrightness:
						Intent activityEditScreenBrightnessIntent = new Intent(ActivityManageSpecificRule.this, ActivityManageBrightnessSetting.class);
//						ActivityEditTriggerUrl.resultingAction = a;
						activityEditScreenBrightnessIntent.putExtra("autoBrightness", a.getParameter1());
						activityEditScreenBrightnessIntent.putExtra("brightnessValue", Integer.parseInt(a.getParameter2()));
						startActivityForResult(activityEditScreenBrightnessIntent, requestCodeActionScreenBrightnessEdit);
						break;
					default:
						Miscellaneous.logEvent("w", "Edit action", "Editing of action type " + a.getAction().toString() + " not implemented, yet.", 4);
						break;				
				}
			}			
		});
		actionListView.setOnTouchListener(new OnTouchListener()
		{			
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		
		chkRuleToggle.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{			
			private boolean guiEditing = false;
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(!guiEditing)
					if(!plausibilityCheck())
					{
						guiEditing = true;
						chkRuleToggle.setChecked(false);
						guiEditing = false;
						Toast.makeText(ActivityManageSpecificRule.this, getResources().getString(R.string.toggleNotAllowed), Toast.LENGTH_LONG).show();
					}						
			}
		});
		
		imageHelpButton.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				// Open help popup
				Miscellaneous.messageBox(getResources().getString(R.string.whatsThis), getResources().getString(R.string.helpTextToggable), ActivityManageSpecificRule.this).show();
			}
		});
	}
	
	protected boolean plausibilityCheck()
	{
		boolean nfcFound = false;
		if(chkRuleToggle.isChecked())
		{
			for(Trigger trigger : ruleToEdit.getTriggerSet())
			{
				if(trigger.getTriggerType().equals(Trigger_Enum.nfcTag))
					nfcFound = true;
			}
			if(!nfcFound)
				return false;
		}
		
		return true;
	}

	protected void loadFormValuesToVariable()
	{
		ruleToEdit.setName(etRuleName.getText().toString());
		ruleToEdit.setRuleActive(chkRuleActive.isChecked());
		ruleToEdit.setRuleToggle(chkRuleToggle.isChecked());
	}

	private void loadVariablesIntoGui()
	{
		// Set all gui fields to the values of the to-be-edited-object
		
		etRuleName.setText(ruleToEdit.getName());
		chkRuleActive.setChecked(ruleToEdit.isRuleActive());
		chkRuleToggle.setChecked(ruleToEdit.isRuleToggle());
		
		refreshTriggerList();
		refreshActionList();
	}

	private AlertDialog getTriggerTypeDialog(final Context myContext)
	{		
		final ArrayList<Item> items = new ArrayList<Item>();
		
		CharSequence[] types = Trigger.getTriggerTypesAsArray();
		CharSequence[] typesLong = Trigger.getTriggerTypesStringAsArray(myContext);
		
		for(int i=0; i<types.length; i++)
		{			
			//pointOfInterest, timeFrame, charging, batteryLevel, usb_host_connection, speed, noiseLevel, wifiConnection, process_started_stopped;
			if(types[i].toString().equals(Trigger_Enum.pointOfInterest.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.compass_small));
			else if(types[i].toString().equals(Trigger_Enum.timeFrame.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.alarm));
			else if(types[i].toString().equals(Trigger_Enum.charging.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.power));
			else if(types[i].toString().equals(Trigger_Enum.batteryLevel.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.battery));
			else if(types[i].toString().equals(Trigger_Enum.usb_host_connection.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.usb));
			else if(types[i].toString().equals(Trigger_Enum.speed.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.speed));
			else if(types[i].toString().equals(Trigger_Enum.noiseLevel.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.ear));
			else if(types[i].toString().equals(Trigger_Enum.wifiConnection.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.wifi));
			else if(types[i].toString().equals(Trigger_Enum.process_started_stopped.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.startprogram));
			else if(types[i].toString().equals(Trigger_Enum.airplaneMode.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.plane));
			else if(types[i].toString().equals(Trigger_Enum.roaming.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.roaming));
			else if(types[i].toString().equals(Trigger_Enum.phoneCall.toString()))
            {
//                if(ActivityPermissions.isPermissionDeclaratedInManifest(ActivityManageSpecificRule.this, "android.permission.SEND_SMS") && !Miscellaneous.isGooglePlayInstalled(ActivityManageSpecificRule.this))
				if(ActivityPermissions.isPermissionDeclaratedInManifest(ActivityManageSpecificRule.this, "android.permission.SEND_SMS"))
                    items.add(new Item(typesLong[i].toString(), R.drawable.phone));
            }
			else if(types[i].toString().equals(Trigger_Enum.nfcTag.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.nfc));
			else if(types[i].toString().equals(Trigger_Enum.activityDetection.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.activitydetection));
			else if(types[i].toString().equals(Trigger_Enum.bluetoothConnection.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.bluetooth));
			else if(types[i].toString().equals(Trigger_Enum.headsetPlugged.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.headphone));
			else
				items.add(new Item(typesLong[i].toString(), R.drawable.placeholder));
		}
			
			ListAdapter adapter = new ArrayAdapter<Item>(this, android.R.layout.select_dialog_item, android.R.id.text1, items)
		    {
		        public View getView(int position, View convertView, ViewGroup parent)
		        {
		            //User super class to create the View
		        	View v = super.getView(position, convertView, parent);
		        	
		        	TextView tv = (TextView)v.findViewById(android.R.id.text1);		            

		            //Put the image on the TextView
		            tv.setCompoundDrawablesWithIntrinsicBounds(items.get(position).icon, 0, 0, 0);

		            //Add margin between image and text (support various screen densities)
		            int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
		            tv.setCompoundDrawablePadding(dp5);

		            return v;
		        }
		    };

			AlertDialog.Builder builder = new AlertDialog.Builder(this)
			    .setTitle(getResources().getString(R.string.selectTypeOfTrigger))
			    .setAdapter(adapter, new DialogInterface.OnClickListener()
			    {
			        public void onClick(DialogInterface dialog, int which)
			        {	
			        	triggerType = Trigger_Enum.values()[which];

						String[] booleanChoices = null;
						if(triggerType == Trigger_Enum.pointOfInterest)
						{
							if(PointOfInterest.getPointOfInterestCollection() != null && PointOfInterest.getPointOfInterestCollection().size() > 0)
								booleanChoices = new String[]{getResources().getString(R.string.entering), getResources().getString(R.string.leaving)};
							else
							{
								Toast.makeText(myContext, getResources().getString(R.string.noPoisSpecified), Toast.LENGTH_LONG).show();
								return;
							}
						}
						else if(triggerType == Trigger_Enum.timeFrame)
						{
							newTrigger.setTriggerType(Trigger_Enum.timeFrame);
							ActivityManageTimeFrame.editedTimeFrameTrigger = newTrigger;
							Intent timeFrameEditor = new Intent(myContext, ActivityManageTimeFrame.class);
							startActivityForResult(timeFrameEditor, requestCodeTriggerTimeframeAdd);
							return;
						}
						else if(triggerType == Trigger_Enum.charging)
							booleanChoices = new String[]{getResources().getString(R.string.started), getResources().getString(R.string.stopped)};
						else if(triggerType == Trigger_Enum.usb_host_connection)
							booleanChoices = new String[]{getResources().getString(R.string.connected), getResources().getString(R.string.disconnected)};
						else if(triggerType == Trigger_Enum.speed | triggerType == Trigger_Enum.noiseLevel | triggerType == Trigger_Enum.batteryLevel)
							booleanChoices = new String[]{getResources().getString(R.string.exceeds), getResources().getString(R.string.dropsBelow)};
						else if(triggerType == Trigger_Enum.wifiConnection)
							booleanChoices = new String[]{getResources().getString(R.string.connected), getResources().getString(R.string.disconnected)};
						else if(triggerType == Trigger_Enum.process_started_stopped)
							booleanChoices = new String[]{getResources().getString(R.string.started), getResources().getString(R.string.stopped)};
						else if(triggerType == Trigger_Enum.airplaneMode)
							booleanChoices = new String[]{getResources().getString(R.string.activated), getResources().getString(R.string.deactivated)};
						else if(triggerType == Trigger_Enum.roaming)
							booleanChoices = new String[]{getResources().getString(R.string.activated), getResources().getString(R.string.deactivated)};
						else if(triggerType == Trigger_Enum.phoneCall)
							booleanChoices = new String[]{getResources().getString(R.string.started), getResources().getString(R.string.stopped)};
						else if(triggerType == Trigger_Enum.activityDetection)
						{
							if(ActivityDetectionReceiver.isPlayServiceAvailable())
							{
								newTrigger.setTriggerType(Trigger_Enum.activityDetection);
								getTriggerActivityDetectionDialog().show();
							}
							else
								Toast.makeText(myContext, getResources().getString(R.string.triggerOnlyAvailableIfPlayServicesInstalled), Toast.LENGTH_LONG).show();
							return;
						}
						else if(triggerType == Trigger_Enum.nfcTag)
						{
							if(NfcReceiver.checkNfcRequirements(ActivityManageSpecificRule.this, true))
							{
								newTrigger.setTriggerType(Trigger_Enum.nfcTag);
								Intent nfcEditor = new Intent(myContext, ActivityManageNfc.class);
								startActivityForResult(nfcEditor, requestCodeTriggerNfcTagAdd);
								return;
							}
						}
						else if(triggerType == Trigger_Enum.bluetoothConnection)
						{
							if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH))
								Miscellaneous.messageBox("Bluetooth", getResources().getString(R.string.deviceDoesNotHaveBluetooth), ActivityManageSpecificRule.this).show();;

							newTrigger.setTriggerType(Trigger_Enum.bluetoothConnection);
							ActivityManageBluetoothTrigger.editedBluetoothTrigger = newTrigger;
							Intent bluetoothEditor = new Intent(myContext, ActivityManageBluetoothTrigger.class);
							startActivityForResult(bluetoothEditor, requestCodeTriggerBluetoothAdd);
							return;
						}
						else if(triggerType == Trigger_Enum.headsetPlugged)
							booleanChoices = new String[]{getResources().getString(R.string.connected), getResources().getString(R.string.disconnected)};

						if(triggerType == Trigger_Enum.nfcTag)
						{
							if (NfcReceiver.checkNfcRequirements(ActivityManageSpecificRule.this, true))
								getTriggerParamterDialog(context, booleanChoices).show();
						}
						else
							getTriggerParamterDialog(context, booleanChoices).show();
			        }
			    });
			
			return builder.create();
	}
	private AlertDialog getTriggerParamterDialog(final Context myContext, final String[] choices)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.selectTypeOfTrigger));
		alertDialogBuilder.setItems(choices, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if(which==0)
					newTrigger.setTriggerParameter(true);
				else
					newTrigger.setTriggerParameter(false);
				
				if(triggerType == Trigger_Enum.pointOfInterest)
				{					
					ArrayList<String> choices = new ArrayList<String>();
					choices.add("< " + getResources().getString(R.string.none) + " >");
					for(String s : PointOfInterest.getNamesInArray())
						choices.add(s);
					
					getTriggerPoiDialog(myContext, choices.toArray(new String[choices.size()])).show();
				}
				else if(triggerType == Trigger_Enum.batteryLevel)
				{					
					ArrayList<String> choicesList = new ArrayList<String>();
					for(int i=1; i<=100; i+=1)
						choicesList.add(String.valueOf(i) + " %");
					String[] choices = (String[]) choicesList.toArray(new String[choicesList.size()]);
					getTriggerBatteryDialog(myContext, choices).show();
				}
				else if(triggerType == Trigger_Enum.speed)
				{					
					ArrayList<String> choicesList = new ArrayList<String>();
					for(int i=5; i<=150; i+=5)
						choicesList.add(String.valueOf(i) + " km/h");
					String[] choices = (String[]) choicesList.toArray(new String[choicesList.size()]);
					getTriggerSpeedDialog(myContext, choices).show();
				}
				else if(triggerType == Trigger_Enum.noiseLevel)
				{					
					ArrayList<String> choicesList = new ArrayList<String>();
					for(int i=5; i<=150; i+=5)
						choicesList.add(String.valueOf(i) + " dB");
					String[] choices = (String[]) choicesList.toArray(new String[choicesList.size()]);
					getTriggerNoiseDialog(myContext, choices).show();
				}
//				else if(triggerType.equals(Trigger.Event_Enum.timeFrame))
//				{
//					newTrigger.setTriggerType(Trigger.Event_Enum.timeFrame);
//					ActivityManageTimeFrame.editedTimeFrameTrigger = null;
//					Intent timeFrameEditor = new Intent(myContext, ActivityManageTimeFrame.class);
//					startActivityForResult(timeFrameEditor, 2000);
//				}
				else if(triggerType.equals(Trigger_Enum.wifiConnection))
				{
					newTrigger.setTriggerType(Trigger_Enum.wifiConnection);
					getTriggerWifiDialog(myContext).show();
				}
				else if(triggerType.equals(Trigger_Enum.process_started_stopped))
				{
					progressDialog = ProgressDialog.show(myContext, null, getResources().getString(R.string.gettingListOfInstalledApplications), true, false);
					newTrigger.setTriggerType(Trigger_Enum.process_started_stopped);
					new GenerateApplicationSelectionsDialogTask().execute(ActivityManageSpecificRule.this);
//					getTriggerRunningProcessDialog1(myContext).show();
				}
				else if(triggerType.equals(Trigger_Enum.phoneCall))
				{
					newTrigger.setTriggerType(Trigger_Enum.phoneCall);
					getTriggerPhoneDirectionDialog(myContext).show();
				}
				else if(triggerType.equals(Trigger_Enum.headsetPlugged))
				{
					newTrigger.setTriggerType(Trigger_Enum.headsetPlugged);
					getTriggerHeadphoneDialog(myContext).show();
				}
				else
				{
					newTrigger.setTriggerType(triggerType);
					ruleToEdit.getTriggerSet().add(newTrigger);
					refreshTriggerList();
				}
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	private AlertDialog getTriggerBatteryDialog(final Context myContext, final String[] choices)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.selectBattery));
		alertDialogBuilder.setItems(choices, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				try
				{
					triggerBattery = (which+1);
					newTrigger.setTriggerType(Trigger_Enum.batteryLevel);
					newTrigger.setBatteryLevel(triggerBattery);
//					Log.i("test", newTrigger.toString());
//					Log.i("test", String.valueOf(newTrigger.getBatteryLevel()));
					ruleToEdit.getTriggerSet().add(newTrigger);
					refreshTriggerList();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	private AlertDialog getTriggerSpeedDialog(final Context myContext, final String[] choices)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.selectSpeed));
		alertDialogBuilder.setItems(choices, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				try
				{
					triggerSpeed = (which+1)*5;
					newTrigger.setTriggerType(Trigger_Enum.speed);
					newTrigger.setSpeed(triggerSpeed);
					ruleToEdit.getTriggerSet().add(newTrigger);
					refreshTriggerList();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	private AlertDialog getTriggerNoiseDialog(final Context myContext, final String[] choices)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.selectNoiseLevel));
		alertDialogBuilder.setItems(choices, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				try
				{
					triggerNoise = (which+1)*5;
					newTrigger.setTriggerType(Trigger_Enum.noiseLevel);
					newTrigger.setNoiseLevelDb(Math.round(triggerNoise));
					ruleToEdit.getTriggerSet().add(newTrigger);
					refreshTriggerList();
					
					/*
					 * Comment about the physical reference value.
					 */
					if(!Rule.isAnyRuleUsing(Trigger_Enum.noiseLevel))
					{
						AlertDialog noiseHintDialog = Miscellaneous.messageBox(myContext.getResources().getString(R.string.hint), myContext.getResources().getString(R.string.noiseDetectionHint), myContext);
						noiseHintDialog.show();
						Linkify.addLinks((TextView) noiseHintDialog.findViewById(android.R.id.message), Linkify.ALL);
//						((TextView)noiseHintDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}	
	private AlertDialog getTriggerWifiDialog(final Context myContext)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(myContext.getResources().getString(R.string.wifiName));
		alertDialog.setMessage(myContext.getResources().getString(R.string.enterWifiName));

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alertDialog.setView(input);

		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				newTrigger.setWifiName(input.getText().toString());
				ruleToEdit.getTriggerSet().add(newTrigger);
				refreshTriggerList();
			}
		});

		alertDialog.setNegativeButton(myContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// Canceled.
			}
		});

		return alertDialog.create();
	}				
	private AlertDialog getTriggerPhoneDirectionDialog(final Context myContext)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(myContext.getResources().getString(R.string.phoneDirection));
		String[] choices = new String[] { myContext.getResources().getString(R.string.any), myContext.getResources().getString(R.string.incoming), myContext.getResources().getString(R.string.outgoing) };
		alertDialog.setItems(choices, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				newTrigger.setPhoneDirection(which);
				getTriggerPhoneNumberDialog(ActivityManageSpecificRule.this).show();
			}
		});

		return alertDialog.create();
	}
	private AlertDialog getTriggerPhoneNumberDialog(final Context myContext)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(myContext.getResources().getString(R.string.phoneNumber));
		alertDialog.setMessage(myContext.getResources().getString(R.string.enterPhoneNumber));

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_PHONE);
		alertDialog.setView(input);

		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				String number = input.getText().toString();
				if(number.length() == 0)
					number = "any";
				
				newTrigger.setPhoneNumber(number);
				ruleToEdit.getTriggerSet().add(newTrigger);
				refreshTriggerList();
			}
		});

		alertDialog.setNegativeButton(myContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// Canceled.
			}
		});

		return alertDialog.create();
	}
	private AlertDialog getTriggerPoiDialog(final Context myContext, final String[] choices)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.selectPoi));
		alertDialogBuilder.setItems(choices, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				try
				{
					if(which > 0)
						triggerPoi = PointOfInterest.getByName(choices[which]);
					else
						triggerPoi = null;
					
					newTrigger.setTriggerType(Trigger_Enum.pointOfInterest);
					newTrigger.setPointOfInterest(triggerPoi);
					ruleToEdit.getTriggerSet().add(newTrigger);
					refreshTriggerList();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	private AlertDialog getTriggerHeadphoneDialog(final Context myContext)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.headphoneSelectType));
		alertDialogBuilder.setItems(new String[]{ myContext.getResources().getString(R.string.headphoneSimple), myContext.getResources().getString(R.string.headphoneMicrophone), myContext.getResources().getString(R.string.headphoneAny) }, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				try
				{	
					newTrigger.setHeadphoneType(which);					
					ruleToEdit.getTriggerSet().add(newTrigger);
					refreshTriggerList();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	private AlertDialog getTriggerActivityDetectionDialog()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(Miscellaneous.getAnyContext().getResources().getString(R.string.selectTypeOfActivity));
		String[] choices = ActivityDetectionReceiver.getAllDescriptions();
		alertDialog.setItems(choices, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				newTrigger.setActivityDetectionType(ActivityDetectionReceiver.getAllTypes()[which]);
				ruleToEdit.getTriggerSet().add(newTrigger);
				refreshTriggerList();
			}
		});

		return alertDialog.create();
	}	
	
	private static class GenerateApplicationSelectionsDialogTask extends AsyncTask<ActivityManageSpecificRule, Void, String[]>
	{
		@Override
		protected String[] doInBackground(ActivityManageSpecificRule... params)
		{
//			Looper.prepare();
			final String[] applicationArray = ActivityManageStartActivity.getApplicationNameListString(params[0]);
			return applicationArray;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			if(progressDialog != null)
			{
				progressDialog.dismiss();
				progressDialog = null;
			}
			
			ActivityManageSpecificRule.getInstance().showProcessDialog(result);
		}
	}
	
	void showProcessDialog(String[] programStrings)
	{
		getTriggerRunningProcessDialog1(ActivityManageSpecificRule.this, programStrings).show();
	}
	
	private AlertDialog getTriggerRunningProcessDialog1(final Context myContext, final String[] applicationArray)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myContext);
		alertDialogBuilder.setTitle(myContext.getResources().getString(R.string.selectApplication));
		alertDialogBuilder.setItems(applicationArray, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String applicationName = applicationArray[which];
				getTriggerRunningProcessDialog2(myContext, applicationName).show();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
//		Log.i("Amount of Applications", String.valueOf(applicationArray.length));
//		Log.i("Amount of Packages", String.valueOf(ActivityManageStartActivity.getPackageListString(myContext).length));
		
		return alertDialog;
	}
	
	private AlertDialog getTriggerRunningProcessDialog2(final Context myContext, String applicationName)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myContext);
		alertDialogBuilder.setTitle(myContext.getResources().getString(R.string.selectPackageOfApplication));
		final String[] packageArray = ActivityManageStartActivity.getPackageListString(myContext, applicationName);
		alertDialogBuilder.setItems(packageArray, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String packageName = packageArray[which];
				getTriggerRunningProcessDialog3(myContext, packageName).show();
				Miscellaneous.messageBox(getResources().getString(R.string.hint), getResources().getString(R.string.chooseActivityHint), ActivityManageSpecificRule.this).show();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}

	private AlertDialog getTriggerRunningProcessDialog3(final Context myContext, final String packageName)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myContext);
		alertDialogBuilder.setTitle(myContext.getResources().getString(R.string.selectActivityToBeStarted));
		final String activityArray[] = ActivityManageStartActivity.getActivityListForPackageName(packageName);
		alertDialogBuilder.setItems(activityArray, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				triggerProcess = activityArray[which];
				newTrigger.setTriggerType(Trigger_Enum.process_started_stopped);
				newTrigger.setProcessName(triggerProcess);
				ruleToEdit.getTriggerSet().add(newTrigger);
				refreshTriggerList();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == requestCodeActionTriggerUrlAdd)
		{
			if(resultCode == RESULT_OK)
			{
				//add TriggerUrl
				ruleToEdit.getActionSet().add(ActivityEditTriggerUrl.resultingAction);
				this.refreshActionList();
			}
		}
		else if(requestCode == requestCodeActionTriggerUrlEdit)
		{
			if(resultCode == RESULT_OK)
			{
				//edit TriggerUrl
				this.refreshActionList();
			}
		}
		else if(requestCode == requestCodeTriggerTimeframeAdd)
		{
			//add TimeFrame
			if(resultCode == RESULT_OK && ActivityManageTimeFrame.editedTimeFrameTrigger != null)
			{
				ruleToEdit.getTriggerSet().add(newTrigger);
				this.refreshTriggerList();
			}
			else
				Miscellaneous.logEvent("w", "TimeFrameEdit", "No timeframe returned. Assuming abort.", 5);
		}
		else if(requestCode == requestCodeTriggerTimeframeEdit)
		{
			//edit TimeFrame
			if(resultCode == RESULT_OK && ActivityManageTimeFrame.editedTimeFrameTrigger != null)
			{
				this.refreshTriggerList();
			}
			else
				Miscellaneous.logEvent("w", "TimeFrameEdit", "No timeframe returned. Assuming abort.", 5);
		}
		else if(requestCode == requestCodeActionStartActivityAdd)
		{
			// manage start of other activity
			if(resultCode == RESULT_OK)
			{
				newAction = ActivityManageStartActivity.resultingAction;
				ruleToEdit.getActionSet().add(newAction);
				this.refreshActionList();
			}
		}
		else if(requestCode == requestCodeActionStartActivityEdit)
		{
			// manage start of other activity
			if(resultCode == RESULT_OK)
			{
				newAction = ActivityManageStartActivity.resultingAction;
//				ruleToEdit.getActionSet().add(newAction);
				this.refreshActionList();
			}
		}
		else if(requestCode == requestCodeTriggerNfcTagAdd)
		{
			//add TimeFrame
			if(resultCode == RESULT_OK && ActivityManageNfc.generatedId != null)
			{
				newTrigger.setNfcTagId(ActivityManageNfc.generatedId);
				ruleToEdit.getTriggerSet().add(newTrigger);
				this.refreshTriggerList();
			}
			else
				Miscellaneous.logEvent("w", "ActivityManageNfc", "No nfc id returned. Assuming abort.", 5);
		}
		else if(requestCode == requestCodeActionSpeakTextAdd)
		{
			if(resultCode == RESULT_OK)
			{
				//add SpeakText
				ruleToEdit.getActionSet().add(ActivityEditSpeakText.resultingAction);
				this.refreshActionList();
			}
		}
		else if(requestCode == requestCodeActionSpeakTextEdit)
		{
			if(resultCode == RESULT_OK)
			{
				//add SpeakText
				ruleToEdit.getActionSet().add(ActivityEditSendTextMessage.resultingAction);
				this.refreshActionList();
			}
		}
		else if(requestCode == requestCodeTriggerBluetoothAdd)
		{
			//add bluetooth trigger
			if(resultCode == RESULT_OK && ActivityManageBluetoothTrigger.editedBluetoothTrigger != null)
			{
				ruleToEdit.getTriggerSet().add(newTrigger);
				this.refreshTriggerList();
			}
			else
				Miscellaneous.logEvent("w", "BluetoothTriggerEdit", "No bluetooth trigger returned. Assuming abort.", 5);
		}
		else if(requestCode == requestCodeTriggerBluetoothEdit)
		{
			//edit bluetooth trigger
			if(resultCode == RESULT_OK && ActivityManageBluetoothTrigger.editedBluetoothTrigger != null)
			{
				this.refreshTriggerList();
			}
			else
				Miscellaneous.logEvent("w", "BluetoothTriggerEdit", "No bluetooth trigger returned. Assuming abort.", 5);
		}
		else if(requestCode == requestCodeActionScreenBrightnessAdd)
		{
			if(resultCode == RESULT_OK)
			{
				newAction.setParameter1(data.getBooleanExtra("autoBrightness", false));
				newAction.setParameter2(String.valueOf(data.getIntExtra("brightnessValue", 0)));
				ruleToEdit.getActionSet().add(newAction);
				this.refreshActionList();
			}
		}
		else if(requestCode == requestCodeActionScreenBrightnessEdit)
		{
			if(resultCode == RESULT_OK)
			{
				if(data.hasExtra("autoBrightness"))
					ruleToEdit.getActionSet().get(editIndex).setParameter1(data.getBooleanExtra("autoBrightness", false));

				if(data.hasExtra("brightnessValue"))
					ruleToEdit.getActionSet().get(editIndex).setParameter2(String.valueOf(data.getIntExtra("brightnessValue", 0)));

				this.refreshActionList();
			}
		}

		//TODO: Check with has data been changed or something like that.
		/*try
		{
			Miscellaneous.logEvent("i", "ActivityManageSpecificRule", getResources().getString(R.string.noDataChangedReadingAnyway), 4);
			XmlFileInterface.readFile();
		}
		catch (FileNotFoundException e)
		{
			Miscellaneous.logEvent("e", "ActivityManageSpecificRule", getResources().getString(R.string.errorReadingPoisAndRulesFromFile) + ": " + Log.getStackTraceString(e), 5);
		}*/
	}

	protected Dialog getActionTypeDialog()
	{
		final ArrayList<Item> items = new ArrayList<Item>();
		
		CharSequence[] types = Action.getActionTypesAsArray();
		CharSequence[] typesLong = Action.getActionTypesFullNameStringAsArray(this);
		
		for(int i=0; i<types.length; i++)
		{			
			if(types[i].toString().equals(Action_Enum.setWifi.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.wifi));
			else if(types[i].toString().equals(Action_Enum.setBluetooth.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.bluetooth));
			else if(types[i].toString().equals(Action_Enum.setUsbTethering.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.router));
			else if(types[i].toString().equals(Action_Enum.setWifiTethering.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.router));
			else if(types[i].toString().equals(Action_Enum.setDisplayRotation.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.displayrotation));
			else if(types[i].toString().equals(Action_Enum.waitBeforeNextAction.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.wait));
			else if(types[i].toString().equals(Action_Enum.setAirplaneMode.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.plane));
			else if(types[i].toString().equals(Action_Enum.wakeupDevice.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.alarm));
			else if(types[i].toString().equals(Action_Enum.changeSoundProfile.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.sound));
			else if(types[i].toString().equals(Action_Enum.triggerUrl.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.triggerurl));
			else if(types[i].toString().equals(Action_Enum.startOtherActivity.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.startprogram));
			else if(types[i].toString().equals(Action_Enum.setDataConnection.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.dataconnection));
			else if(types[i].toString().equals(Action_Enum.speakText.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.talking));
			else if(types[i].toString().equals(Action_Enum.playMusic.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.tune));
			else if(types[i].toString().equals(Action_Enum.setScreenBrightness.toString()))
				items.add(new Item(typesLong[i].toString(), R.drawable.brightness));
			else if(types[i].toString().equals(Action_Enum.sendTextMessage.toString()))
			{
//			    if(ActivityPermissions.isPermissionDeclaratedInManifest(ActivityManageSpecificRule.this, "android.permission.SEND_SMS") && !Miscellaneous.isGooglePlayInstalled(ActivityManageSpecificRule.this))
				if(ActivityPermissions.isPermissionDeclaratedInManifest(ActivityManageSpecificRule.this, "android.permission.SEND_SMS"))
					items.add(new Item(typesLong[i].toString(), R.drawable.message));
			}
			else
				items.add(new Item(typesLong[i].toString(), R.drawable.placeholder));
		}
			
//			= 	{
//								    new Item("Bluetooth", R.drawable.bluetooth),
//								    new Item("Wifi", R.drawable.wifi),
//								    new Item("...", 0), //no icon for this one
//								};

//			ListAdapter adapter = new ArrayAdapter<Item>(this, android.R.layout.select_dialog_item, android.R.id.text1, items)
			ListAdapter adapter = new ArrayAdapter<Item>(this, android.R.layout.select_dialog_item, android.R.id.text1, items)
		    {
		        public View getView(int position, View convertView, ViewGroup parent)
		        {
		            //User super class to create the View
		        	View v = super.getView(position, convertView, parent);
		        	
		        	TextView tv = (TextView)v.findViewById(android.R.id.text1);		            

		            //Put the image on the TextView
		            tv.setCompoundDrawablesWithIntrinsicBounds(items.get(position).icon, 0, 0, 0);

		            //Add margin between image and text (support various screen densities)
		            int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
		            tv.setCompoundDrawablePadding(dp5);

		            return v;
		        }
		    };

			AlertDialog.Builder builder = new AlertDialog.Builder(this)
			    .setTitle(getResources().getString(R.string.selectTypeOfAction))
			    .setAdapter(adapter, new DialogInterface.OnClickListener()
			    {
			        public void onClick(DialogInterface dialog, int which)
			        {	
						newAction = new Action();
						
						if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.triggerUrl.toString()))
						{
							//launch other activity to enter a url and parameters;
							newAction.setAction(Action_Enum.triggerUrl);
							ActivityEditTriggerUrl.resultingAction = null;
							Intent editTriggerIntent = new Intent(context, ActivityEditTriggerUrl.class);
							startActivityForResult(editTriggerIntent, 1000);
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.setWifi.toString()))
						{
							newAction.setAction(Action_Enum.setWifi);
							if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
								Toast.makeText(context, context.getResources().getString(R.string.android10WifiToggleNotice), Toast.LENGTH_LONG).show();
							getActionParameter1Dialog(ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.setBluetooth.toString()))
						{
							if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH))
								Miscellaneous.messageBox("Bluetooth", getResources().getString(R.string.deviceDoesNotHaveBluetooth), ActivityManageSpecificRule.this).show();;
							newAction.setAction(Action_Enum.setBluetooth);
							getActionParameter1Dialog(ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.setUsbTethering.toString()))
						{
							newAction.setAction(Action_Enum.setUsbTethering);
							if(Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1)
								Toast.makeText(context, context.getResources().getString(R.string.usbTetheringFailForAboveGingerbread), Toast.LENGTH_LONG).show();
							getActionParameter1Dialog(ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.setWifiTethering.toString()))
						{
							newAction.setAction(Action_Enum.setWifiTethering);
							getActionParameter1Dialog(ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.setDisplayRotation.toString()))
						{
							newAction.setAction(Action_Enum.setDisplayRotation);
							getActionParameter1Dialog(ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.changeSoundProfile.toString()))
						{
							if(Profile.getProfileCollection().size() > 0)
							{
								newAction.setAction(Action_Enum.changeSoundProfile);
								getActionSoundProfileDialog(context).show();
							}
							else
								Toast.makeText(context, getResources().getString(R.string.noProfilesCreateOneFirst), Toast.LENGTH_LONG).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.startOtherActivity.toString()))
						{
							newAction.setAction(Action_Enum.startOtherActivity);
							Intent intent = new Intent(ActivityManageSpecificRule.this, ActivityManageStartActivity.class);
							startActivityForResult(intent, 3000);
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.waitBeforeNextAction.toString()))
						{
							newAction.setAction(Action_Enum.waitBeforeNextAction);
							getActionWaitBeforeNextActionDialog(ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.wakeupDevice.toString()))
						{
							newAction.setAction(Action_Enum.wakeupDevice);
							getActionWakeupDeviceDialog(ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.setAirplaneMode.toString()))
						{
							if(Build.VERSION.SDK_INT >= 17)
							{
								Toast.makeText(context, getResources().getString(R.string.airplaneModeSdk17Warning), Toast.LENGTH_LONG).show();
								Miscellaneous.messageBox(getResources().getString(R.string.airplaneMode), getResources().getString(R.string.rootExplanation), ActivityManageSpecificRule.this).show();
							}
							newAction.setAction(Action_Enum.setAirplaneMode);
							getActionParameter1Dialog(ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.setDataConnection.toString()))
						{
							newAction.setAction(Action_Enum.setDataConnection);
							getActionParameter1Dialog(ActivityManageSpecificRule.this).show();
							Miscellaneous.messageBox(getResources().getString(R.string.actionDataConnection), getResources().getString(R.string.rootExplanation), ActivityManageSpecificRule.this).show();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.speakText.toString()))
						{
							//launch other activity to enter a url and parameters;
							newAction.setAction(Action_Enum.speakText);
							ActivityEditSpeakText.resultingAction = null;
							Intent editTriggerIntent = new Intent(context, ActivityEditSpeakText.class);
							startActivityForResult(editTriggerIntent, 5000);
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.sendTextMessage.toString()))
						{
//                            if(ActivityPermissions.isPermissionDeclaratedInManifest(ActivityManageSpecificRule.this, "android.permission.SEND_SMS") && !Miscellaneous.isGooglePlayInstalled(ActivityManageSpecificRule.this))
							if(ActivityPermissions.isPermissionDeclaratedInManifest(ActivityManageSpecificRule.this, "android.permission.SEND_SMS"))
                            {
                                //launch other activity to enter parameters;
                                newAction.setAction(Action_Enum.sendTextMessage);
                                ActivityEditSendTextMessage.resultingAction = null;
                                Intent editTriggerIntent = new Intent(context, ActivityEditSendTextMessage.class);
                                startActivityForResult(editTriggerIntent, 5001);
                            }
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.playMusic.toString()))
						{
							newAction.setAction(Action_Enum.playMusic);
							ruleToEdit.getActionSet().add(newAction);
							refreshActionList();
						}
						else if(Action.getActionTypesAsArray()[which].toString().equals(Action_Enum.setScreenBrightness.toString()))
						{
							newAction.setAction(Action_Enum.setScreenBrightness);
							Intent actionScreenBrightnessIntent = new Intent(context, ActivityManageBrightnessSetting.class);
							startActivityForResult(actionScreenBrightnessIntent, requestCodeActionScreenBrightnessAdd);
						}
			        }
			    });
			
			return builder.create();
	}
	private AlertDialog getActionSoundProfileDialog(final Context myContext)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.selectSoundProfile));
		
		final String[] choices;
//		choices = new String[]{"silent", "vibrate", "normal"};
		ArrayList<String> list = Profile.getProfileCollectionString();
		choices = list.toArray(new String[list.size()]);
		
		alertDialogBuilder.setItems(choices, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				newAction.setParameter2(choices[which]);
				ruleToEdit.getActionSet().add(newAction);
				refreshActionList();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	
	private AlertDialog getTriggerDeleteDialog(Context myContext, final Trigger triggerToBeDeleted)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.whatToDoWithTrigger));
		alertDialogBuilder.setItems(new String[]{getResources().getString(R.string.delete)}, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// Only 1 choice at the moment, no need to check
				ruleToEdit.getTriggerSet().remove(triggerToBeDeleted);
				refreshTriggerList();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	private AlertDialog getActionDeleteDialog(Context myContext, final Action actionToBeDeleted)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(getResources().getString(R.string.whatToDoWithAction));
		alertDialogBuilder.setItems(new String[] { getResources().getString(R.string.delete), getResources().getString(R.string.moveUp), getResources().getString(R.string.moveDown)}, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				int positionOfSelected;
				switch(which)
				{
					case 0:
						// Delete
						if(ruleToEdit.getActionSet().remove(actionToBeDeleted))
						{
							refreshActionList();
						}
						break;
					case 1:
						// Move up
						positionOfSelected = ruleToEdit.getActionSet().indexOf(actionToBeDeleted);
						if(positionOfSelected > 0)
						{
							Miscellaneous.logEvent("i", "Swap", "Swapping positions " + String.valueOf(positionOfSelected) + " and " + String.valueOf(positionOfSelected-1), 4);
							Collections.swap(ruleToEdit.getActionSet(), positionOfSelected, positionOfSelected-1);
							refreshActionList();
						}
						else
						{
							// Is already at the top
							Toast.makeText(ActivityManageSpecificRule.this, getResources().getString(R.string.cantMoveUp), Toast.LENGTH_LONG).show();
						}
						break;
					case 2:
						// Move down
						positionOfSelected = ruleToEdit.getActionSet().indexOf(actionToBeDeleted);
						Miscellaneous.logEvent("i", "Swap", "Swapping positions " + String.valueOf(positionOfSelected) + " and " + String.valueOf(positionOfSelected+1), 4);
						if(positionOfSelected < ruleToEdit.getActionSet().size()-1)
						{
							Collections.swap(ruleToEdit.getActionSet(), positionOfSelected, positionOfSelected+1);
							refreshActionList();
						}
						else
						{
							// Is already at the bottom
							Toast.makeText(ActivityManageSpecificRule.this, getResources().getString(R.string.cantMoveDown), Toast.LENGTH_LONG).show();
						}
						break;
				}
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}

	/*private AlertDialog getActionStartActivityDialog1(final Context myContext)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(myContext.getResources().getString(R.string.selectApplication));
		final String[] applicationArray = ActivityManageStartActivity.getApplicationNameListString(ActivityManageSpecificRule.this);
		alertDialogBuilder.setItems(applicationArray, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				getActionStartActivityDialog2(myContext, applicationArray[which]).show();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	private AlertDialog getActionStartActivityDialog2(final Context myContext, String applicationName)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(myContext.getResources().getString(R.string.selectPackageOfApplication));
		final String[] packageArray = ActivityManageStartActivity.getPackageListString(ActivityManageSpecificRule.this, applicationName);
		alertDialogBuilder.setItems(packageArray, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				getActionStartActivityDialog3(ActivityManageSpecificRule.this, packageArray[which]).show();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}
	private AlertDialog getActionStartActivityDialog3(final Context myContext, final String packageName)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(myContext.getResources().getString(R.string.selectActivityToBeStarted));
		final String activityArray[] = ActivityManageStartActivity.getActivityListForPackageName(packageName);
		alertDialogBuilder.setItems(activityArray, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				ActivityInfo ai = ActivityManageStartActivity.getActivityInfoForPackageNameAndActivityName(packageName, activityArray[which]);
//				Log.i("Selected", ai.packageName + " / " + ai.name);
				newAction.setParameter2(ai.packageName + ";" + ai.name);
				newAction.toString();
				ruleToEdit.getActionSet().add(newAction);
				refreshActionList();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}*/

	private AlertDialog getActionWaitBeforeNextActionDialog(final Context myContext)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(myContext.getResources().getString(R.string.waitBeforeNextAction));
		alertDialog.setMessage(myContext.getResources().getString(R.string.waitBeforeNextActionEnterValue));

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alertDialog.setView(input);

		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				if(input.getText().toString().length() == 0| input.getText().toString().equals("0") | input.getText().toString().contains(",") | input.getText().toString().contains("."))
				{
					Toast.makeText(myContext, ActivityManageSpecificRule.this.getResources().getString(R.string.enterAPositiveValidNonDecimalNumber), Toast.LENGTH_LONG).show();
					getActionWaitBeforeNextActionDialog(ActivityManageSpecificRule.this).show();
				}
				else
				{
					newAction.setParameter2(input.getText().toString());
					newAction.toString();
					ruleToEdit.getActionSet().add(newAction);
					refreshActionList();
				}
			}
		});

		alertDialog.setNegativeButton(myContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// Canceled.
			}
		});

		return alertDialog.create();
	}

	private AlertDialog getActionWakeupDeviceDialog(final Context myContext)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(myContext.getResources().getString(R.string.wakeupDevice));
		alertDialog.setMessage(myContext.getResources().getString(R.string.wakeupDeviceValue));

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alertDialog.setView(input);

		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				if(input.getText().toString().length() == 0| input.getText().toString().contains(",") | input.getText().toString().contains("."))
				{
					Toast.makeText(myContext, ActivityManageSpecificRule.this.getResources().getString(R.string.enterAPositiveValidNonDecimalNumber), Toast.LENGTH_LONG).show();
					getActionWakeupDeviceDialog(ActivityManageSpecificRule.this).show();
				}
				else
				{
					newAction.setParameter2(input.getText().toString());
					newAction.toString();
					ruleToEdit.getActionSet().add(newAction);
					refreshActionList();
				}
			}
		});

		alertDialog.setNegativeButton(myContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// Canceled.
			}
		});

		return alertDialog.create();
	}
	
	private AlertDialog getActionParameter1Dialog(final Context myContext)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(myContext.getResources().getString(R.string.selectToggleDirection));
		final String choices[] = { myContext.getString(R.string.activate), myContext.getString(R.string.deactivate) };
		alertDialogBuilder.setItems(choices, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if(which == 0)
					newAction.setParameter1(true);
				else
					newAction.setParameter1(false);
				
				ruleToEdit.getActionSet().add(newAction);
				refreshActionList();				
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		return alertDialog;
	}	
	
	protected void refreshTriggerList()
	{
		Miscellaneous.logEvent("i", "ListView", "Attempting to update TriggerListView", 4);
		if(triggerListView.getAdapter() == null)
		{
			triggerListView.setAdapter(triggerListViewAdapter);
		}
		triggerListViewAdapter.notifyDataSetChanged();
	}
	protected void refreshActionList()
	{
		Miscellaneous.logEvent("i", "ListView", "Attempting to update ActionListView", 4);
		if(actionListView.getAdapter() == null)
		{
			actionListView.setAdapter(actionListViewAdapter);
		}
		actionListViewAdapter.notifyDataSetChanged();
	}
	
	public static class Item
	{
	    public final String text;
	    public final int icon;
	    
	    public Item(String text, Integer icon)
	    {
	        this.text = text;
	        this.icon = icon;
	    }
	    
	    @Override
	    public String toString() {
	        return text;
	    }
	}

	protected void hideKeyboard()
	{
		View view = this.getCurrentFocus();
		if (view != null)
		{
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
}
