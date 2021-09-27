package com.example.fragment.placeholder;

import com.example.fragment.ItemOnclickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class WordContent implements ItemOnclickListener{

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<WordItem> ITEMS = new ArrayList<WordItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, WordItem> ITEM_MAP = new HashMap<String, WordItem>();

    private static final int COUNT = 25;

    static {
        addItem(new WordItem("0", "Apple", "苹果", "I want an apple."));
        addItem(new WordItem("1", "Orange", "橙子", "Would you care for some orange juice?"));
        addItem(new WordItem("2", "Banana", "香蕉", "He slipped on a banana skin."));
        addItem(new WordItem("3", "Lemon", "柠檬", "A lemon is an acid fruit."));
        addItem(new WordItem("4", "Pear", "梨子", "There are lots of pear trees near the house."));
    }

    private static void addItem(WordItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    @Override
    public void onItemClick() {

    }


    /**
     * A placeholder item representing a piece of content.
     */
    public static class WordItem {
        public final String id;
        public final String word;
        public final String meaning;
        public final String sample;

        public WordItem(String id, String word, String meaning, String sample) {
            this.id = id;
            this.word = word;

            this.meaning = meaning;
            this.sample = sample;
        }

        @Override
        public String toString() {
            return "WordItem{" +
                    "id='" + id + '\'' +
                    ", word='" + word + '\'' +
                    ", meaning='" + meaning + '\'' +
                    ", sample='" + sample + '\'' +
                    '}';
        }
    }
}