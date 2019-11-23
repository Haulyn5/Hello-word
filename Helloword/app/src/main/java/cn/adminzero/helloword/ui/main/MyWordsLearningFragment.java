package cn.adminzero.helloword.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import cn.adminzero.helloword.R;
import cn.adminzero.helloword.util.Words;
import cn.adminzero.helloword.util.WordsUtil;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyWordsLearningFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static MyWordsLearningFragment newInstance(int index) {
        MyWordsLearningFragment fragment = new MyWordsLearningFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // pageViewModel是自动生成的代码
        //pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        // 提取自己是第几个Fragment
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        //pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_check_out_words, container, false);
        // 将数据库里的单词导入到活动中显示
        ListView listView = root.findViewById(R.id.word_list_view);
        List<Words> wordsList = new ArrayList<Words>();
        Words words1 = new Words();
        Words words2 = new Words();
        words1.setWord("Abandon");
        words1.setPhonetic("[əˈbændən]");
        words2.setWord("freedom");
        words2.setPhonetic("[ˈfriːdəm]");
        /*wordsList.add(WordsUtil.getWordById((short)1));
        wordsList.add(WordsUtil.getWordById((short)2));*/
        wordsList.add(words1);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        wordsList.add(words2);
        Log.e(TAG, "onCreateView: "+wordsList.toString() );
        MyWordsLearningFragment.List_adapter list_adapter =
                new MyWordsLearningFragment.List_adapter(this.getContext(),
                        R.layout.listview_word, wordsList);
        listView.setAdapter(list_adapter);
        return root;
    }

    // 用于列表显示单词 TODO 修改布局以更美观更通用
    public class List_adapter extends ArrayAdapter<Words> {
        private int resourceId;

        public List_adapter(Context context, int textViewResourceId, List<Words> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            Words words = getItem(position);//获取当前项的Account实例

            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            // int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.WRITE_EXTERNAL_STORAGE);

            //  final View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ImageButton pronounceButton = (ImageButton) view.findViewById(R.id.pronounceButton);
            TextView word_content_textView = (TextView) view.findViewById(R.id.word_content_textView);
            TextView word_phonetic_textView = (TextView) view.findViewById(R.id.word_phonetic_textView);

            word_content_textView.setText(words.getWord());
            word_phonetic_textView.setText(words.getPhonetic());
            //debug
            return  view;
        }


    }
}