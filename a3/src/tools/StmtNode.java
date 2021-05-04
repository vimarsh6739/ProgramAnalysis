package tools;

public class StmtNode extends BB {
    
    Field x,y,z;
    String sx,sy,sz;
    public StmtNode(NodeType op,int bbid,int tid,String ann,Field x,Field y,Field z,String sx,String sy,String sz) {
        super(op,bbid,tid,ann);
        this.x=x;
        this.y=y;
        this.z=z;
        this.sx=sx;
        this.sy=sy;
        this.sz=sz;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": Label:"+this.ann+"\t\t");
        
        if(op == NodeType.PRINT){sb.append("System.out.println("+sx+");");}
        else if(op == NodeType.ALLOCATE){sb.append(x.name+" = new "+x.type+"();");}
        else if(op == NodeType.ADD){sb.append(sx+" = "+sy+"+"+sz+";");}
        else if(op == NodeType.AND){sb.append(sx+" = "+sy+"&&"+sz+";");}
        else if(op == NodeType.MULT){sb.append(sx+" = "+sy+"*"+sz+";");}
        else if(op == NodeType.SUB){sb.append(sx+" = "+sy+"-"+sz+";");}
        else if(op == NodeType.LT){sb.append(sx+" = "+sy+"<"+sz+";");}
        else if(op == NodeType.FIELDREAD){sb.append(sx+" = "+sy+"."+sz+";");}
        else if(op == NodeType.FIELDASSIGN){sb.append(sx+"."+sy+"="+sz+";");}
        else if(op == NodeType.NOT){sb.append(sx+" = !"+sy+";");}
        else if(op == NodeType.ASSIGN){sb.append(sx+" = "+sy+";");}
        sb.append("\n");
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
