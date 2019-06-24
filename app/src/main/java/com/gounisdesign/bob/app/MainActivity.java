package com.gounisdesign.bob.app;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class  MainActivity  extends BlunoLibrary  implements RadioGroup.OnCheckedChangeListener{

	private static SeekBar seek_bar;
	private static TextView text_view;

	RadioGroup radioGroup;
	RadioButton radioButton;

	private RadioButton radioButton1, radioButton2, radioButton3;
	private Button buttonSelected;

	String toSend = "C07A";

	private Button buttonScan;
	private Button buttonSerialSend;
	private EditText serialSendText;
	private TextView serialReceivedText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        onCreateProcess();														//onCreate Process by BlunoLibrary


        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data
        serialSendText=(EditText) findViewById(R.id.serialSendText);			//initial the EditText of the sending data

        buttonSerialSend = (Button) findViewById(R.id.buttonSerialSend);		//initial the button for sending the data
        buttonSerialSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//serialSend(serialSendText.getText().toString());				//send the data to the BLUNO
				serialSend(toSend);
			}
		});

        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
			}
		});

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(this);

		radioButton1 = (RadioButton)findViewById(R.id.radio_easy);
		radioButton2 = (RadioButton)findViewById(R.id.radio_inter);
		radioButton3 = (RadioButton)findViewById(R.id.radio_hard);


		seekbar();
	}

//	public void checkedButton(View v) {
//		int radioId = radioGroup.getCheckedRadioButtonId();
//
//		radioButton = findViewById(radioId);
//
//		Toast.makeText(this, "selected :" + radioButton.getText(), Toast.LENGTH_SHORT).show();
//	}

	public void seekbar(){
		seek_bar = (SeekBar)findViewById(R.id.seekBar);
		text_view = (TextView)findViewById(R.id.textView);
		text_view.setText("7 seconds");

		seek_bar.setOnSeekBarChangeListener(
				new SeekBar.OnSeekBarChangeListener() {

					String firstChar;
					String levelChars;
					String lastChar;
					String changed;
					int progress_value;
					@Override
					public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
						progress_value = i;
						if (i == 1) {
							text_view.setText(i + " second");
						} else {
							text_view.setText(i + " seconds");
						}
						//Toast.makeText(MainActivity.this, "SeekBar in progress", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						firstChar = toSend.substring(0,1);
						levelChars = toSend.substring(1,3);
						lastChar = toSend.substring(3);
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						if (progress_value  == 1) {
							text_view.setText(progress_value  + " second");
						} else {
							text_view.setText(progress_value  + " seconds");
						}
						if (progress_value < 10) {
							if (progress_value == 0) {
								progress_value = 1;
							}
							changed = String.valueOf(progress_value);
							changed = "0" + changed;
						} else {
							changed = String.valueOf(progress_value);
						}
						toSend = firstChar + changed + lastChar;
					}
				}
		);
	}

	protected void onResume(){
		super.onResume();
		System.out.println("BlUNOActivity onResume");
		onResumeProcess();														//onResume Process by BlunoLibrary
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
		super.onActivityResult(requestCode, resultCode, data);
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }
	
	protected void onStop() {
		super.onStop();
		onStopProcess();														//onStop Process by BlunoLibrary
	}
    
	@Override
    protected void onDestroy() {
        super.onDestroy();	
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

	@Override
	public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
		switch (theConnectionState) {											//Four connection state
		case isConnected:
			buttonScan.setText("Disconnect");
			break;
		case isConnecting:
			buttonScan.setText("Connecting");
			break;
		case isToScan:
			buttonScan.setText("Scan");
			break;
		case isScanning:
			buttonScan.setText("Scanning");
			break;
		case isDisconnecting:
			buttonScan.setText("isDisconnecting");
			break;
		default:
			break;
		}
	}

	@Override
	public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
		// TODO Auto-generated method stub
		serialReceivedText.setText(theString);							//append the text into the EditText
		//The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
		((ScrollView)serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int i) {
		char[] sendArray = toSend.toCharArray();
		switch (i) {
			case R.id.radio_easy:
				//sendArray[0] = 'A';
				//toSend = sendArray.toString();
				toSend = toSend.substring(0,3) + "A";
						Toast.makeText(this, "Mode: " + "Easy", Toast.LENGTH_SHORT).show();
						break;

			case R.id.radio_inter:
				toSend = toSend.substring(0,3) + "B";
				Toast.makeText(this, "Mode: " + "Medium", Toast.LENGTH_SHORT).show();
						break;

			case R.id.radio_hard:
				toSend = toSend.substring(0,3) + "C";
				Toast.makeText(this, "Mode: " + "Hard", Toast.LENGTH_SHORT).show();
					break;
		}
	}
}