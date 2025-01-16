// Generated by view binder compiler. Do not edit!
package com.example.project.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.project.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDialogRatingBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button buttonSubmitRating;

  @NonNull
  public final EditText editTextRating;

  private ActivityDialogRatingBinding(@NonNull LinearLayout rootView,
      @NonNull Button buttonSubmitRating, @NonNull EditText editTextRating) {
    this.rootView = rootView;
    this.buttonSubmitRating = buttonSubmitRating;
    this.editTextRating = editTextRating;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDialogRatingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDialogRatingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_dialog_rating, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDialogRatingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonSubmitRating;
      Button buttonSubmitRating = ViewBindings.findChildViewById(rootView, id);
      if (buttonSubmitRating == null) {
        break missingId;
      }

      id = R.id.editTextRating;
      EditText editTextRating = ViewBindings.findChildViewById(rootView, id);
      if (editTextRating == null) {
        break missingId;
      }

      return new ActivityDialogRatingBinding((LinearLayout) rootView, buttonSubmitRating,
          editTextRating);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}