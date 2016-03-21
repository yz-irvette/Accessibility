package com.example.yi.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        System.out.println("[EVENT] " + event);

        AccessibilityNodeInfo source = event.getSource();
        if (source != null) {
            System.out.println("[NodeInfo] " + source);
            int type = event.getEventType();
            CharSequence className = event.getClassName();
            //if (type == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
            /*
                String resName = source.getViewIdResourceName();
                if (resName != null) {
                    System.out.println(resName);
                } else {
                    System.out.println("res name null");
                }*/
            //} else
            if (type == AccessibilityEvent.TYPE_VIEW_SCROLLED && className != null) {
                    System.out.println(className);
                    if (className.equals("android.support.v4.view.ViewPager")) {
                        //source.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                    } else if (className.equals("android.view.ViewGroup")) {
                        //source.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                    }
            } else if (className != null && className.equals("android.widget.Switch")) {
                AccessibilityNodeInfo parent = source.getParent();
                if (parent != null) {
                    /*
                    List<AccessibilityNodeInfo> brightnesses = parent.findAccessibilityNodeInfosByText("Automatic brightness");
                    for (AccessibilityNodeInfo brightness : brightnesses) {
                        System.out.println("[BRIGHTNESS]"+brightness);
                    }
                    */
                    int child_num = parent.getChildCount();
                    for (int i = 0; i < child_num; i++) {
                        System.out.println("[CHILD] "+parent.getChild(i));
                    }

                }
            } else if (className != null && className.equals("android.widget.EditText")) {
                // First
                Bundle arguments = new Bundle();
                arguments.putCharSequence(AccessibilityNodeInfo
                        .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "android");
                //source.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                // Second
                //source.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                //ClipData clip = ClipData.newPlainText("label", "DATA" );
                //clipboard.setPrimaryClip(clip);
                //source.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            } else if (type == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
                    && event.getPackageName().equals("com.android.deskclock")
                    && className != null && className.equals("android.widget.TextView")) {
                /* set text not working for view
                System.out.println("hi0 "+source.getText());
                Pattern pattern = Pattern.compile("[0-9][0-9]:[0-9][0-9]");
                Matcher matcher = pattern.matcher(source.getText());
                if (matcher.find()) {
                    System.out.println("hi1");
                    Bundle arguments = new Bundle();
                    arguments.putCharSequence(AccessibilityNodeInfo
                            .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "09:09");
                    source.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                }
                */
            }
            source.recycle();

        }

        /*
        // Grab the parent of the view that fired the event.
        AccessibilityNodeInfo rowNode = source.getParent();
        if (rowNode == null) {
            return;
        }

        // Using this parent, get references to both child nodes, the label and the checkbox.
        AccessibilityNodeInfo labelNode = rowNode.getChild(0);
        if (labelNode == null) {
            rowNode.recycle();
            return;
        }

        AccessibilityNodeInfo completeNode = rowNode.getChild(1);
        if (completeNode == null) {
            rowNode.recycle();
            return;
        }

        // Determine what the task is and whether or not it's complete, based on
        // the text inside the label, and the state of the check-box.
        if (rowNode.getChildCount() < 2 || !rowNode.getChild(1).isCheckable()) {
            rowNode.recycle();
            return;
        }

        CharSequence taskLabel = labelNode.getText();
        final boolean isComplete = completeNode.isChecked();
        String completeStr;

        if (isComplete) {
            completeStr = "checked";
        } else {
            completeStr = "not_checked";
        }
        String reportStr = taskLabel + completeStr;
        //Log.d("accessibility", reportStr);
        System.out.println(reportStr);
        */
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public boolean onGesture(int gestureId) {
        switch (gestureId) {
            case GESTURE_SWIPE_UP:
                System.out.println("swipe up");
            case GESTURE_SWIPE_DOWN:
                System.out.println("swipe down");
            case GESTURE_SWIPE_LEFT:
                System.out.println("swipe left");
            case GESTURE_SWIPE_RIGHT:
                System.out.println("swipe right");
            case GESTURE_SWIPE_LEFT_AND_RIGHT:
                System.out.println("swipe left right");
            case GESTURE_SWIPE_DOWN_AND_RIGHT:
                System.out.println("swipe down right");
        }
        return super.onGesture(gestureId);
    }

    @Override
    public void onServiceConnected() {

        AccessibilityServiceInfo info = getServiceInfo();
        // Set the type of events that this service wants to listen to.  Others
        // won't be passed to this service.
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;

        // If you only want this service to work with specific applications, set their
        // package names here.  Otherwise, when the service is activated, it will listen
        // to events from all applications.
        //info.packageNames = new String[]
        //        {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};

        // Set the type of feedback your service will provide.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated.  This service *is*
        // application-specific, so the flag isn't necessary.  If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

        //info.flags = AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE; //

        info.notificationTimeout = 100;

        setServiceInfo(info);

    }
}