// Generated code from Butter Knife. Do not modify!
package com.ycexample.ycapp.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ycexample.ycapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScanCodeActivity_ViewBinding implements Unbinder {
  private ScanCodeActivity target;

  private View view2131361928;

  private View view2131361929;

  @UiThread
  public ScanCodeActivity_ViewBinding(ScanCodeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ScanCodeActivity_ViewBinding(final ScanCodeActivity target, View source) {
    this.target = target;

    View view;
    target.etCurrentScanCode = Utils.findRequiredViewAsType(source, R.id.et_current_scan_code, "field 'etCurrentScanCode'", TextView.class);
    target.ivCurrentStatusColor = Utils.findRequiredViewAsType(source, R.id.iv_current_status_color, "field 'ivCurrentStatusColor'", ImageView.class);
    target.tvScanCodeCount = Utils.findRequiredViewAsType(source, R.id.tv_scan_code_count, "field 'tvScanCodeCount'", TextView.class);
    target.lvScanCodeList = Utils.findRequiredViewAsType(source, R.id.lv_scan_code_list, "field 'lvScanCodeList'", ListView.class);
    target.ivSerialPortStatus = Utils.findRequiredViewAsType(source, R.id.iv_serial_port_status, "field 'ivSerialPortStatus'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.btn_stop_fail, "field 'btnStopFail' and method 'onViewClicked'");
    target.btnStopFail = Utils.castView(view, R.id.btn_stop_fail, "field 'btnStopFail'", Button.class);
    view2131361928 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_clear_data, "field 'btnClearData' and method 'onViewClicked'");
    target.btnClearData = Utils.castView(view, R.id.btn_clear_data, "field 'btnClearData'", Button.class);
    view2131361929 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ScanCodeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etCurrentScanCode = null;
    target.ivCurrentStatusColor = null;
    target.tvScanCodeCount = null;
    target.lvScanCodeList = null;
    target.ivSerialPortStatus = null;
    target.btnStopFail = null;
    target.btnClearData = null;

    view2131361928.setOnClickListener(null);
    view2131361928 = null;
    view2131361929.setOnClickListener(null);
    view2131361929 = null;
  }
}
