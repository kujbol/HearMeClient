package com.kprod.hearme.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.kprod.hearme.BR;
import com.kprod.hearme.HearMeApp;
import com.kprod.hearme.R;
import com.kprod.hearme.data.api.HearMeService;
import com.kprod.hearme.data.api.model.Conversation;
import com.kprod.hearme.databinding.ConversationsMainBinding;
import com.kprod.hearme.ui.binder.ConversationBinder;
import com.kprod.hearme.ui.recyclerview_binding.adapter.ClickHandler;
import com.kprod.hearme.ui.recyclerview_binding.adapter.LongClickHandler;
import com.kprod.hearme.ui.recyclerview_binding.adapter.binder.CompositeItemBinder;
import com.kprod.hearme.ui.recyclerview_binding.adapter.binder.ItemBinder;
import com.kprod.hearme.ui.viewmodel.ConversationViewModel;
import com.kprod.hearme.ui.viewmodel.ConversationsViewModel;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConversationsActivity extends AppCompatActivity {
    private ConversationsViewModel conversationsViewModel;
    private ConversationsMainBinding binding;

    private HearMeService hearMeService;
    private Subscription subscription;

    @Nullable
    private static String getStringFromEditText(EditText editText) {
        Editable editable = editText.getText();
        return editable == null ? null : editable.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversationsViewModel = new ConversationsViewModel();
        conversationsViewModel.conversations.add(new ConversationViewModel(new Conversation("Szymon Stankiewicz", "1193192101", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/1796434_1094656857233009_3590585128600476928_n.jpg?oh=dfa3435071500ced5bcfb2a19390bc1e&oe=57D57AE7", "whats up")));

        loadServices();

        subscription = hearMeService.getConversations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(conversationsViewModel);

        binding = DataBindingUtil.setContentView(this, R.layout.conversations_main);
        binding.setConversationsViewModel(conversationsViewModel);
        binding.setView(this);
        binding.activityConversationsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversationsViewModel.addConversation("Szymon Stankiewicz", "1193192101", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/1796434_1094656857233009_3590585128600476928_n.jpg?oh=dfa3435071500ced5bcfb2a19390bc1e&oe=57D57AE7", "whats up");
            }
        };
    }

    public ClickHandler<ConversationViewModel> clickHandler() {
        return new ClickHandler<ConversationViewModel>() {
            @Override
            public void onClick(ConversationViewModel conversation) {
                //Toast.makeText(UsersView.this, user.getFirstName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public LongClickHandler<ConversationViewModel> longClickHandler() {
        return new LongClickHandler<ConversationViewModel>() {
            @Override
            public void onLongClick(ConversationViewModel conversation) {
                // Toast.makeText(UsersView.this, "LONG CLICK: " + user.getFirstName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public ItemBinder<ConversationViewModel> itemViewBinder() {
        return new CompositeItemBinder<ConversationViewModel>(
                new ConversationBinder(BR.conversation, R.layout.item_conversation)
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subscription != null)
            subscription.unsubscribe();
    }

    private void loadServices() {
        hearMeService = ((HearMeApp) getApplication()).hearMeService;
    }
}