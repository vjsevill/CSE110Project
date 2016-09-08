package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.rip.roomies.activities.home.Home;
import com.rip.roomies.models.Bulletin;
import com.rip.roomies.util.InfoStrings;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class represents a container for multiple BulletinView objects that can
 * be displayed in a dynamic group.
 */
public class BulletinContainer extends LinearLayout {
    private static final Logger log = Logger.getLogger(BulletinContainer.class.getName());

    private ArrayList<Bulletin> bulletins = new ArrayList<>();

    /**
     * @see android.view.View(Context)
     */
    public BulletinContainer(Context context) {
        super(context);
    }

    /**
     * @see android.view.View(Context, AttributeSet)
     */
    public BulletinContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @see android.view.View(Context, AttributeSet, int)
     */
    public BulletinContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Adds a new bulletin to the BulletinContainer at the end of the list.
     *
     * @param newBull The new bulletin to add
     */
    public void addBulletin(Bulletin newBull) {
        log.info(String.format(InfoStrings.CONTAINER_ADD,
                BulletinView.class.getName(),  BulletinContainer.class.getName()));

        //add our newly created bill to our dynamic arraylist of bills.
        bulletins.add(newBull);

        BulletinView bullView = new BulletinView(getContext(), this, (Home)getContext());
        bullView.setBulletin(newBull);
        addView(bullView);
    }

    public void removeBulletin(Bulletin bullToRemove) {
        //log statement

        //remove the bulletin from the dynamic list of bulletins
        for (int i = 0; i < bulletins.size(); i++) {
            if (bulletins.get(i).getRowID() == bullToRemove.getRowID()) {
                    bulletins.remove(i);
            }
        }
    }

    /**
     * Get the bulletins held by this BulletinContainer
     * @return An array of bulletins
     */
    public Bulletin[] getBulletins() {
        Bulletin[] temp = new Bulletin[bulletins.size()];
        return bulletins.toArray(temp);
    }
}
