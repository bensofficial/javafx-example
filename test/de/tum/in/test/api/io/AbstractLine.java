package de.tum.in.test.api.io;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.util.Objects;

import static de.tum.in.test.api.localization.Messages.localized;

@API(status = Status.MAINTAINED)
public abstract class AbstractLine implements de.tum.in.test.api.io.Line {

    int lineNumber = -1;

    static boolean containsLineBreaks(CharSequence text) {
        for (var i = 0; i < text.length(); i++) {
            var character = text.charAt(i);
            if (character == '\r' || character == '\n')
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return lineNumber == -1 ? localized("abstract_line.plain_line", text()) //$NON-NLS-1$
                : localized("abstract_line.numbered_line", lineNumber, text()); //$NON-NLS-1$
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractLine other))
            return false;
        return Objects.equals(text(), other.text());
    }

    @Override
    public int hashCode() {
        return text().hashCode();
    }

    @Override
    public final int lineNumber() {
        return lineNumber;
    }

    public final void setLineNumber(int lineNumber) {
        if (lineNumber <= 0)
            throw new IllegalArgumentException("Line number cannot be set to " + lineNumber); //$NON-NLS-1$
        if (this.lineNumber != -1)
            throw new IllegalStateException("Line number has already been set"); //$NON-NLS-1$
        this.lineNumber = lineNumber;
    }
}
