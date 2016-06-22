package com.kprod.hearme.ui.binder;

import com.kprod.hearme.ui.recyclerview_binding.adapter.binder.ConditionalDataBinder;
import com.kprod.hearme.ui.viewmodel.ConversationViewModel;

public class ConversationBinder extends ConditionalDataBinder<ConversationViewModel> {
    public ConversationBinder(int bindingVariable, int layoutId) {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(ConversationViewModel model) {
        return true;
    }
}