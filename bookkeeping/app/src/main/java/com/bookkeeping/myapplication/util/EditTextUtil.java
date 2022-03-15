package com.bookkeeping.myapplication.util;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * 创建日期：$ $
 *
 * @author Dong Yuxiang
 * @since 文件名称： $
 * 类说明：
 */
public class EditTextUtil {
    public static void forbidCopy(EditText etPass) {
        /**
         * 禁止粘贴
         * */
        etPass.setLongClickable(false);
        etPass.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
    }
}
