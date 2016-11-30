package com.tencent.richeditor.view;

/**
 * Created by quinn on 11/30/16.
 */

public interface FormatInterface {

    public void bold(int start, int end, boolean format);

    public boolean cantainBold(int start, int end);

    public void italic(int start, int end, boolean format);

    public boolean containItalic(int start, int end);

    public void underLine(int start, int end, boolean format);

    public boolean containUnderLine(int start, int end);

    public void strikeThrough(int start, int end, boolean format);

    public boolean containStrikeThrough(int start, int end);

    public void addCheckBox();





}
