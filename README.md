# PopupBubble
Easily Add  "New Posts" popup button with the feeds (recyclerview) of your app.

<img src="https://github.com/HemantGTX950/PopupBubble/blob/master/1.png" align="left" height="700" width="400" >
<img src="https://github.com/HemantGTX950/PopupBubble/blob/master/1.png" height="700" width="400" >

# Min SDK
Minimum sdk is 16 and support is limited to recyclerview for now.

# Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
# Add the dependency
```
compile 'com.github.HemantGTX950:PopupBubble:v0.1.0'
```

# Add PopupBubble to layout
```xml
<com.hb.popupbble.PopupBubble
  android:id="@+id/popup_bubble"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
/>
```

Positioning of this view can be done according to the need. By default it should be placed in <b>top center</b>. Also it should be placed <b>below recyclerview</b> in layout so that it shows on top of recyclerview.

# Example positioning
If its inside RelativeLayout then
```xml
<com.hb.popupbble.PopupBubble
  android:layout_margin="16dp"
  android:id="@+id/popup_bubble"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:layout_centerHorizontal="true"
/>
```
# Customization Through XML
```xml
<!--Change background Color-->
    app:pb_backgroundColor="?attr/colorPrimary"
<!--Change text -->
    app:pb_text="New Stories"
<!--Change text color-->
    app:pb_textColor="#ffffff"
<!--Show/Hide Icon inside the button. By default its true.-->
    app:pb_showIcon="false"
<!--Change icon color-->
    app:pb_iconColor="#ffffff"
<!--Set Different Icons-->
    app:pb_icon="@drawable/ic_new.png"
<!--Set different fonts-->
    app:pb_font="iran_sans_mobile.ttf"
        
```
**Example : Full Customization**
```xml
<com.hb.popupbble.PopupBubble
  xmlns:app="http://schemas.android.com/apk/res-auto"
  android:layout_margin="16dp"
  android:id="@+id/popup_bubble"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:layout_centerHorizontal="true"
  app:pb_backgroundColor="?attr/colorPrimary"
  app:pb_iconColor="#ffffff"
  app:pb_text="New Stories"
  app:pb_textColor="#ffffff"
  app:pb_icon="@drawable/ic_keyboard_arrow_up_white_18dp"
  app:pb_font="iran_sans_mobile.ttf"
/>

```
#Access the popupBubble from Java
```java
PopupBubble popupBubble = (PopupBubble) findViewById(R.id.popup_bubble);
```
Add listener if you want to know when the bubble is clicked
```java
popupBubble.setPopupBubbleListener(new PopupBubble.PopupBubbleClickListener() {
            @Override
            public void bubbleClicked(Context context) {
  
                //popup_bubble is clicked  
            }
        });
```
**Attach with your RecyclerView**
```java
 //necessary to add
popupBubble.setRecyclerView(recyclerView);
```
#Helper Methods
**Hide/Show** PopupBubble according to your need
```java
popupBubble.hide();
popupBubble.show();
```
**Control Animations**
Set false if you dont want any animations. Default value is true.
```java
popupBubble.withAnimation(false);
```
**Update text dynamically**
Call this method before the activate method to set the new text.
```java
popupBubble.updateText("10 new stories");
```

**Update icon dynamically**
Call this method before the activate method to set the new Icon.
```java
popupBubble.updateIcon(R.drawable.new_icon);
```

**Update typeface of text dynamically**
Call this method before the activate method to set the new Typeface.
```java
popupBubble.updateTypeFace(myCustomTypeface);
```

# Most Important
Now <b>download/fetch new content</b> in background and then notify your recyclerview adapter about range of items added and finally <b>activate the PopupBubble</b> to make it appear with animation (if not set false).
```java

//Own logic for fetching new content
 .....      
adapter.notifyItemRangeInserted(0,size_of_new_items_added); // size_of_new_items_added = 10 if 10 new items are added.
popupBubble.activate(); 
    
```

# License

```

```

