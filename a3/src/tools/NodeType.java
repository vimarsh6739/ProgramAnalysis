package tools;

public enum NodeType {
    NOP,                                        // nop
    
    BLOCK,                                      // block node

    AND,LT,ADD,SUB,MULT,FIELDREAD,FIELDASSIGN,  // basic statement nodes
    ALLOCATE, ASSIGN, NOT,                          
    
    IF_ELSE,                                    // if node
    
    WHILE,                                      // while node
    
    SYNC,                                       // synchronized node
    
    PRINT,                                      // print node
    
    START, JOIN, WAIT, NOTIFY, NOTIFYALL,       // message nodes
    
    WAITING_PRED, NOTIFIED_ENTRY,               // waiting and notified-entry nodes
    
    BEGIN, END,                                 // method begin and end nodes

    ENTRY, EXIT                                 // sync block entry and exit nodes
}
