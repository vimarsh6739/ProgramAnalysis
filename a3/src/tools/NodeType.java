package tools;

public enum NodeType {
    NOP,                                        // default node
    BLOCK,                                      // block node
    AND,LT,ADD,SUB,MULT,FIELDREAD,FIELDASSIGN,  // basic statement nodes
    IF_ELSE,                                    // if node
    WHILE,                                      // while node
    SYNC,                                       // synchronized node
    PRINT,                                      // print node
    START, JOIN, WAIT,NOTIFY,NOTIFYALL,         // message nodes
    WAIT_PRED, NOTIFY_ENTRY,                    // waiting and notified-entry nodes
    BEGIN, END                                  // cfg entry and exit nodes
}
