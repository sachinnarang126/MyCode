package xyz.truehrms.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import xyz.truehrms.R;

public class ExpandableTextView extends TextView {

    private static final int DEFAULT_TRIM_LENGTH = 200;
    private static final String ELLIPSIS = "..Read More";
    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);
        typedArray.recycle();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trim = !trim;
                setText();
                requestFocusFromTouch();
            }
        });
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
            Spannable spannable = new SpannableString(ELLIPSIS);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorPrimary)), 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(spannable);
        } else {
            return originalText;
        }
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public int getTrimLength() {
        return trimLength;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }
}
