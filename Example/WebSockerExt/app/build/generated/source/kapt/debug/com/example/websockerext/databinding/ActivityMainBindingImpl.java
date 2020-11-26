package com.example.websockerext.databinding;
import com.example.websockerext.R;
import com.example.websockerext.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.guideline, 3);
        sViewsWithIds.put(R.id.rvClient, 4);
        sViewsWithIds.put(R.id.linearLayout, 5);
        sViewsWithIds.put(R.id.etClient, 6);
        sViewsWithIds.put(R.id.linearLayout2, 7);
        sViewsWithIds.put(R.id.etServer, 8);
        sViewsWithIds.put(R.id.rvServer, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    private OnClickListenerImpl mActClientClickAndroidViewViewOnClickListener;
    private OnClickListenerImpl1 mActServerClickAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[1]
            , (android.widget.Button) bindings[2]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[8]
            , (androidx.constraintlayout.widget.Guideline) bindings[3]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[7]
            , (androidx.recyclerview.widget.RecyclerView) bindings[4]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            );
        this.btnClientSend.setTag(null);
        this.btnServerSend.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.act == variableId) {
            setAct((com.example.websockerext.MainActivity) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAct(@Nullable com.example.websockerext.MainActivity Act) {
        this.mAct = Act;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.act);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.example.websockerext.MainActivity act = mAct;
        android.view.View.OnClickListener actClientClickAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener actServerClickAndroidViewViewOnClickListener = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (act != null) {
                    // read act::clientClick
                    actClientClickAndroidViewViewOnClickListener = (((mActClientClickAndroidViewViewOnClickListener == null) ? (mActClientClickAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mActClientClickAndroidViewViewOnClickListener).setValue(act));
                    // read act::serverClick
                    actServerClickAndroidViewViewOnClickListener = (((mActServerClickAndroidViewViewOnClickListener == null) ? (mActServerClickAndroidViewViewOnClickListener = new OnClickListenerImpl1()) : mActServerClickAndroidViewViewOnClickListener).setValue(act));
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            this.btnClientSend.setOnClickListener(actClientClickAndroidViewViewOnClickListener);
            this.btnServerSend.setOnClickListener(actServerClickAndroidViewViewOnClickListener);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private com.example.websockerext.MainActivity value;
        public OnClickListenerImpl setValue(com.example.websockerext.MainActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.clientClick(arg0); 
        }
    }
    public static class OnClickListenerImpl1 implements android.view.View.OnClickListener{
        private com.example.websockerext.MainActivity value;
        public OnClickListenerImpl1 setValue(com.example.websockerext.MainActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.serverClick(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): act
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}