package tools;

public class BeginNode extends BB {
    public BeginNode(NodeType op,int bbid,int tid) {
        super(op,bbid,tid,null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("BB"+bbid+": (method begin)\t");
        
        sb.append("Entry\n");

        sb.append("In edges = [");
        String delim = "";
        for(BB f : this.inEdges){
            sb.append(f.bbid);
            delim = ",";
        }
        sb.append("]\nOut edges = [");
        delim = "";
        for(BB f : this.outEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nCross edges = [");
        for(BB f : this.crossEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n\n");

        return sb.toString();
    }
}