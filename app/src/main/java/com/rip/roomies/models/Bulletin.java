package com.rip.roomies.models;

import com.rip.roomies.sql.SQLCreate;
import com.rip.roomies.sql.SQLGet;
import com.rip.roomies.sql.SQLModify;
import com.rip.roomies.sql.SQLRemove;
import com.rip.roomies.util.InfoStrings;

import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/21/16.
 */
public class Bulletin {

    private int rowID = 0;
    private int groupID = 0;
    private String content = "";

    private static final Logger log = Logger.getLogger(Bulletin.class.getName());

    //------- CONSTRUCTORS -------//

    public Bulletin() {}

    /**
     * Constructor used for adding new bulletins to the database
     *
     * @param content The string this bulletin displays.
     */
    public Bulletin(String content) {
        this.content = content;
    }

    /**
     * Constructor used for getting stored bulletins and inserting new ones
     *
     * @param rowID The unique bulletin ID.
     * @param groupID The ID of the group this bulletin belongs to.
     * @param content The string this bulletin displays.
     */
    public Bulletin(int rowID, int groupID, String content) {
        this.rowID = rowID;
        this.groupID = groupID;
        this.content = content;
    }


    //------- DATABASE METHODS -------//

    /**
     * Connects to the database, creating a new Bulletin with the information provided.
     *
     * @return The newly created Bulletin.
     */
    public Bulletin createBulletin() {
        log.info(InfoStrings.CREATE_BULLETIN_MODEL);

        return SQLCreate.createBulletin(this);
    }

    public Bulletin removeBulletin(int rowID) {
        log.info(InfoStrings.REMOVE_BULLETIN_MODEL);
        return SQLRemove.removeBulletin(rowID);
    }

    public void modifyBulletin(Bulletin bulletinToModify) {
        log.info(InfoStrings.MODIFY_BULLETIN_MODEL);
        SQLModify.modifyBulletin(bulletinToModify);
    }


    //-------------Getter/setter methods------------//

    public int getRowID() { return rowID; }

    public int getGroupID() { return groupID; }

    public String getContent() {
        return content;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }
}
