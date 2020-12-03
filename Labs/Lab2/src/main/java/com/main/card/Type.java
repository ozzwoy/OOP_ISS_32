package com.main.card;

import java.util.Objects;

public class Type {
    private boolean sent;
    private Kind kind;

    public Type() {
        sent = false;
        kind = Kind.SIMPLE;
    }

    public Type(boolean sent, Kind kind) {
        this.sent = sent;
        this.kind = kind;
    }

    public boolean isSent() {
        return sent;
    }

    public Kind getKind() {
        return kind;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return sent == type.sent &&
                kind == type.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sent, kind);
    }
}
