package tools;

public class WhileNode extends BB {
    Field cond;
    BB body;

    public WhileNode(NodeType op, int bbid,int tid, String ann, Field cond) {
        super(op,bbid,tid,ann);
        this.cond=cond;
        this.body=null;
    }

    @Override
    public void addNode(BB blk) {
        this.body = blk;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": (while block)\t");
        
        sb.append("WHILE("+cond.name+"){\n");
        st.nestIndent += "\t";
        sb.append(body.toString());
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);
        sb.append(st.nestIndent+"}\tBB"+bbid+": (while block end)\n\n");
        
        return sb.toString();
    }
}
