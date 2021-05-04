package tools;

public class StmtNode extends BB {
    
    Field x,y,z;

    public StmtNode(NodeType op,int bbid,int tid,String ann,Field x,Field y,Field z) {
        super(op,bbid,tid,ann);
        this.x=x;
        this.y=y;
        this.z=z;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+":\t");
        
        sb.append("Lambda("+x.name+")\n");

        sb.append(st.nestIndent+"In edges = [");
        String delim = "";
        for(BB f : this.inEdges){
            sb.append(f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"Out edges = [");
        delim = "";
        for(BB f : this.outEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"Cross edges = [");
        for(BB f : this.crossEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n\n");
        
        return sb.toString();
    }
}
