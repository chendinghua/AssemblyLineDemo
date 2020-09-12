// Generated code from Butter Knife. Do not modify!
package com.ycexample.ycapp.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ycexample.ycapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScanCodeAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ScanCodeAdapter.ViewHolder target;

  @UiThread
  public ScanCodeAdapter$ViewHolder_ViewBinding(ScanCodeAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvTitleScanCodeId = Utils.findRequiredViewAsType(source, R.id.tv_title_scan_code_id, "field 'tvTitleScanCodeId'", TextView.class);
    target.tvTitleScanCode = Utils.findRequiredViewAsType(source, R.id.tv_title_scan_code, "field 'tvTitleScanCode'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ScanCodeAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitleScanCodeId = null;
    target.tvTitleScanCode = null;
  }
}
