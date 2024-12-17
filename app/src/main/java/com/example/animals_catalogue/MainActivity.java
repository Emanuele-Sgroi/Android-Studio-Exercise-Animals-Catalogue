package com.example.animals_catalogue;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;

public class MainActivity extends AppCompatActivity {

    // UI Elements
    private ImageView mainImageView;
    private TextView animalTitle, animalDescription;
    private Button buttonPrevious, buttonNext;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout imageContainer;
    private ConstraintLayout rootLayout;

    // Data
    private int currentIndex = 0;

    private final String[] animalTitles = {"Capybara", "Chinchilla", "Guinea Pig", "Wombat"};
    private final int[] animalImages = {R.drawable.capybara, R.drawable.chinchilla, R.drawable.guinea_pig, R.drawable.wombat};

    // Animals descriptions
    private String capyDesc = "Ever heard of a creature that looks like a guinea pig got super-sized and decided to take up yoga? That's the capybara for you! Capybaras are the ultimate socialites of the animal kingdom, forming tight-knit groups and casually hanging out in hot springs like they're having a spa day.\n" +
            "\n" +"If there's one lesson to learn from these oversized hamsters, it's to live life at your own pace. So, just channel your inner capybara, find a sunny spot, and take a nap like a pro.";

   private String chinDesc = "A fuzzball so soft, it feels like you're petting a cloud, with eyes so big and adorable, they could melt the heart of even the grumpiest of grumps. That's the chinchilla – nature's equivalent of a living, breathing stuffed animal.\n" +
            "\n" +"These tiny bundles of joy are like the Energizer Bunnies of the rodent world, bouncing around with boundless enthusiasm like they've had one too many espressos. Seriously, they've got moves that would put even the most hyperactive toddler to shame.";


   private String pigDesc = "Imagine a furry little potato with legs, a snout that's basically a built-in snack detector. That's the guinea pig – nature's pocket-sized bundle of joy.\n" +
            "\n" +"Don't let their pudgy appearance fool you – guinea pigs are fierce explorers. Give them a maze, and they'll navigate it with the determination of a detective solving a case. And don't even get me started on their vocal talents – these little chatterboxes have a repertoire of squeaks, chirps, and wheeks that could rival a choir of angels.";


    private String wombatDesc = "Adorable creature that's part teddy bear, part tank, and entirely adorable. That's the wombat – the furry bulldozer of the Australian outback.\n" +
            "\n" +"These rotund rascals are like the engineers of the animal kingdom, with a talent for digging burrows that would make even the most seasoned miners jealous. Seriously, they're like the underground architects of the bush, crafting cozy homes with all the comforts of a luxury cave.";

    private final String[] animalDescriptions = {
            capyDesc, chinDesc, pigDesc, wombatDesc
    };

    private ImageView[] scrollImages;

    // Gesture Detector for Swipe
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        mainImageView = findViewById(R.id.imageAnimal);
        animalTitle = findViewById(R.id.animalTitle);
        animalDescription = findViewById(R.id.animalDescription);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);
        horizontalScrollView = findViewById(R.id.horizontalScroll);
        imageContainer = findViewById(R.id.imageContainer);

        // Load horizontal scroll images
        loadScrollImages();

        // Set up initial animal
        updateAnimal();

        // Button Listeners
        buttonPrevious.setOnClickListener(v -> moveToAnimal(-1));
        buttonNext.setOnClickListener(v -> moveToAnimal(1));

        // Gesture Detector for Swipe
        gestureDetector = new GestureDetector(this, new GestureListener());
        rootLayout = findViewById(R.id.rootLayout);
        rootLayout.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void loadScrollImages() {
        scrollImages = new ImageView[animalImages.length];
        for (int i = 0; i < animalImages.length; i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(animalImages[i]);
            img.setLayoutParams(new LinearLayout.LayoutParams(350, 300));
            img.setPadding(10, 10, 10, 10);
            int finalI = i;
            img.setOnClickListener(v -> goToSpecificAnimal(finalI));
            imageContainer.addView(img);
            scrollImages[i] = img;
        }
    }

    private void updateAnimal() {
        // Update the main image, title, and description
        mainImageView.setImageResource(animalImages[currentIndex]);
        animalTitle.setText(animalTitles[currentIndex]);
        animalDescription.setText(animalDescriptions[currentIndex]);

        // Animate Fade
        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(mainImageView, "alpha", 0f, 1f);
        fadeAnim.setDuration(500);
        fadeAnim.start();

        // Highlight the active image in the horizontal scroll
        for (int i = 0; i < scrollImages.length; i++) {
            scrollImages[i].setAlpha(i == currentIndex ? 1f : 0.4f);
        }

        // Auto-scroll to the active image
        scrollToActiveImage();
    }

    private void moveToAnimal(int step) {
        currentIndex += step;
        if (currentIndex < 0) currentIndex = animalImages.length - 1;
        if (currentIndex >= animalImages.length) currentIndex = 0;
        updateAnimal();
    }

    private void goToSpecificAnimal(int index) {
        currentIndex = index;
        updateAnimal();
    }

    private void scrollToActiveImage() {
        View targetView = scrollImages[currentIndex];
        horizontalScrollView.smoothScrollTo(targetView.getLeft(), 0);
    }

    // Gesture Listener for Swiping
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > 100) {
                moveToAnimal(1);
                return true;
            } else if (e2.getX() - e1.getX() > 100) {
                moveToAnimal(-1);
                return true;
            }
            return false;
        }
    }
}
