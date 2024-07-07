# PinView

A fully customizable PinView library for Android.

<p><img src="media/demo1.gif" width="600" />
<img src="media/demo2.gif" width="600" /></p>

## Dependency

``` Groovy
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}

dependencies {
        implementation 'com.github.neophron88:PinView:1.4.2'
}
```

## Usage

You need to provide a `pinItemLayout` that will be inflated as much as you specify in the `pinCount`
attribute.
Each entered character will be displayed in items corresponding to the position.

Creating an item layout in `layout/item_layout.xml`

``` xml
<FrameLayout 
    android:id="@+id/pin_container"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginHorizontal="5dp"
    android:background="@color/orange_200"
    android:clipToPadding="false"
    android:paddingHorizontal="10dp"
    android:paddingVertical="13dp">
    
    <View
        android:id="@+id/pin_cursor"
        android:layout_width="1.8dp"
        android:layout_height="26sp"
        android:layout_gravity="center"
        android:background="@color/orange_200"
        android:visibility="invisible" />
    
    <TextView
        android:id="@+id/pin_text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:ems="1"
        android:fontFamily="@font/montserrat_medium"
        android:gravity="center"
        android:textColor="@color/white"
        android:textSize="20sp"
        tools:text="M" />

</FrameLayout>

```

Add PinView to your XML like any other view and provide `pinItemLayout` attribute.

``` xml
<com.pulchukur.pinview.PinView
    android:id="@+id/pin_view"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:pinCount="6"
    app:pinImeOptions="actionDone"
    app:pinInputType="number"
    app:pinItemLayout="@layout/item_layout_1" />
```

In order for PinView to at least start responding to input, you need to specify the ID view from
pinItemLayout, which should appear and disappear depending on the characters entered.

``` xml
<com.pulchukur.pinview.PinView
    ...
    app:pinAppearanceBehaviorApplyToViewWithId="@id/pin_text_view"
    ...
/>
```

Above it was indicated which view from pinItemLayout will have the Appearance visual behavior. In
our case it is `@id/pin_text_view` from `@layout/item_layout`.

it's the result:
<p><img src="media/appear.gif" width="582" /></p>

You can change the animation type or duration time via attributes `pinAppearanceBehaviorAnimation`, `pinAppearanceBehaviorAnimationDuration`.

``` xml
<com.pulchukur.pinview.PinView
    ...
    app:pinAppearanceBehaviorAnimation="fromBottomToBottom"
    app:pinAppearanceBehaviorAnimationDuration="300"
    ...
/>
```


If you want to change the visual of an item depending on item state(Active,InActiveFilled,InActiveEmpty), specify `pinTransitionBehaviorApplyToViewWithId`.
Specify the colors  `pinTransitionBehaviorColorActive`, `pinTransitionBehaviorColorEmptyInactive`, `pinTransitionBehaviorColorFilledInactive` ( by default has pinTransitionBehaviorColorActive color).
`pinTransitionBehaviorDrawableShape` attribute , valid values - rectangle or oval.
`pinTransitionBehaviorDrawableShapeColorTransitionAttr` - This xml attribute specifies the color of which drawable attribute to change when the state changes, valid values - solid, stroke, none

``` xml
<com.pulchukur.pinview.PinView
    ...
    app:pinTransitionBehaviorApplyToViewWithId="@id/pin_container"
    app:pinTransitionBehaviorColorActive="@color/yellow"
    app:pinTransitionBehaviorColorEmptyInactive="@color/white"
    app:pinTransitionBehaviorDrawableShape="rectangle"
    app:pinTransitionBehaviorDrawableShapeColorTransitionAttr="stroke"
    app:pinTransitionBehaviorDrawableShapeCornersRadius="12dp"
    app:pinTransitionBehaviorDrawableShapeStrokeWidth="2dp"
    ...
/>
```

It is also possible to specify default static colors for drawable attributes using `pinTransitionBehaviorDrawableShapeStrokeColor` and `pinTransitionBehaviorDrawableShapeSolidColor`.

it's the result:
<p><img src="media/transition.gif" width="552" /></p>

For the cursor we use `pinCursorBehaviorApplyToViewWithId`

``` xml
<com.pulchukur.pinview.PinView
    ...
    app:pinCursorBehaviorApplyToViewWithId="@id/pin_cursor"
    ...
/>
```

PinView text change listener:
``` kotlin
val pinView = findViewById<PinView>(R.id.pin_view)

pinView.pinAddOnTextChangeListener { TODO() }
```

Also possible to set a forced state
``` kotlin
val pinView = findViewById<PinView>(R.id.pin_view)
pinView.pinForcedState = PinView.State.Error
pinView.pinForcedState = PinView.State.Success
pinView.pinForcedState = PinView.State.Default
```

By default for pinTransitionBehaviorView, the error color is red, and the color for success is green, but you can change it if necessary using attributes `pinTransitionBehaviorColorError` and `pinTransitionBehaviorColorSuccess`
``` xml
<com.pulchukur.pinview.PinView
    ...
   app:pinTransitionBehaviorColorSuccess="@color/green"
   app:pinTransitionBehaviorColorError="@color/red"
    ...
/>
```