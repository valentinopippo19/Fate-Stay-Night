package fate;

import java.util.*;
import java.util.function.IntConsumer;

/**
 * Núcleo algorítmico del proyecto. Todas las técnicas están implementadas
 * explícitamente para poder demostrarlas desde la interfaz web.
 */
public final class Algoritmos {
  private Algoritmos(){}

  // ---------- Complejidad / ordenamiento ----------
  public static int[] mergeSort(){int[] a=ids(); mergeSort(a,0,a.length-1); return a;}
  public static int[] quickSort(){int[] a=ids(); quickSort(a,0,a.length-1); return a;}
  private static int[] ids(){int[] a=new int[Datos.N];for(int i=0;i<a.length;i++)a[i]=i;return a;}
  private static void mergeSort(int[]a,int l,int r){if(l>=r)return;int m=(l+r)>>>1;mergeSort(a,l,m);mergeSort(a,m+1,r);int[]t=new int[r-l+1];int i=l,j=m+1,k=0;while(i<=m&&j<=r)t[k++]=Datos.PODER[a[i]]>=Datos.PODER[a[j]]?a[i++]:a[j++];while(i<=m)t[k++]=a[i++];while(j<=r)t[k++]=a[j++];System.arraycopy(t,0,a,l,t.length);}
  private static void quickSort(int[]a,int l,int r){if(l>=r)return;int i=l,j=r,p=Datos.PODER[a[(l+r)>>>1]];while(i<=j){while(Datos.PODER[a[i]]>p)i++;while(Datos.PODER[a[j]]<p)j--;if(i<=j){int x=a[i];a[i++]=a[j];a[j--]=x;}}if(l<j)quickSort(a,l,j);if(i<r)quickSort(a,i,r);}
  public static String mergeTrace(){return sortTrace("merge");}
  public static String sortTrace(String algoritmo){int[] a="quick".equalsIgnoreCase(algoritmo)?quickSort():mergeSort();StringBuilder s=new StringBuilder();s.append("ORDENAMIENTO ").append("quick".equalsIgnoreCase(algoritmo)?"QUICKSORT":"MERGESORT").append("\\n");for(int i:a)s.append(Datos.NOMBRE[i]).append(" -> ").append(Datos.PODER[i]).append("\\n");s.append("\\nDivide y conquista: O(n log n) promedio.\\n");if("quick".equalsIgnoreCase(algoritmo))s.append("Quicksort: O(n²) peor caso, O(log n) espacio promedio por recursión.");else s.append("MergeSort: O(n log n) y O(n) espacio auxiliar.");return s.toString();}

  // ---------- Greedy ----------
  public static int[] greedy(int presupuesto,StringBuilder log){Integer[] a=new Integer[Datos.N];for(int i=0;i<Datos.N;i++)a[i]=i;Arrays.sort(a,(x,y)->Double.compare((double)Datos.PODER[y]/Datos.COSTO[y],(double)Datos.PODER[x]/Datos.COSTO[x]));int[]o=new int[Datos.N];int n=0,c=0;for(int i:a){double r=(double)Datos.PODER[i]/Datos.COSTO[i];log.append(String.format("%-18s ratio %.2f",Datos.NOMBRE[i],r));if(c+Datos.COSTO[i]<=presupuesto){o[n++]=i;c+=Datos.COSTO[i];log.append(" -> TOMAR\\n");}else log.append(" -> DESCARTAR\\n");}log.append("\\nGreedy decide localmente por poder/costo; no garantiza óptimo en toda instancia.\\n");return Arrays.copyOf(o,n);}

  // ---------- Programación dinámica ----------
  public static int[] mochila(int presupuesto,StringBuilder log){int[][]dp=new int[Datos.N+1][presupuesto+1];for(int i=1;i<=Datos.N;i++)for(int c=0;c<=presupuesto;c++){dp[i][c]=dp[i-1][c];if(Datos.COSTO[i-1]<=c)dp[i][c]=Math.max(dp[i][c],dp[i-1][c-Datos.COSTO[i-1]]+Datos.PODER[i-1]);}int[]tmp=new int[Datos.N];int n=0,c=presupuesto;for(int i=Datos.N;i>0;i--)if(dp[i][c]!=dp[i-1][c]){tmp[n++]=i-1;c-=Datos.COSTO[i-1];}log.append("Óptimo global: ").append(dp[Datos.N][presupuesto]).append(" poder\\nMatriz DP: ").append(Datos.N+1).append(" x ").append(presupuesto+1).append("\\n");return Arrays.copyOf(tmp,n);}
  public static int maxComboDP(int id,int mana,StringBuilder log){int[]cost={12,18,25,35,50};int[]dmg={80,130,190,280,410};int[]dp=new int[mana+1];for(int i=0;i<cost.length;i++)for(int m=mana;m>=cost[i];m--)dp[m]=Math.max(dp[m],dp[m-cost[i]]+dmg[i]);log.append("DP de habilidades para ").append(Datos.NOMBRE[id]).append("\\nMana disponible: ").append(mana).append("\\nDaño máximo por selección 0/1: ").append(dp[mana]).append("\\n");return dp[mana];}

  // ---------- Backtracking / exploración exhaustiva ----------
  public static int[] backtracking(int slots,int objetivo,StringBuilder log){int[]sol=new int[slots];Arrays.fill(sol,-1);boolean[]u=new boolean[Datos.N];if(bt(0,slots,objetivo,0,sol,u,log))return sol;return new int[0];}
  private static boolean bt(int p,int slots,int obj,int power,int[]sol,boolean[]u,StringBuilder log){if(p==slots)return power>=obj;for(int i=0;i<Datos.N;i++){if(u[i])continue;boolean dup=false;for(int j=0;j<p;j++)if(Datos.CLASE[sol[j]].equals(Datos.CLASE[i]))dup=true;if(dup)continue;sol[p]=i;u[i]=true;log.append("  ".repeat(p)).append("Elegir ").append(Datos.NOMBRE[i]).append(" (poder ").append(Datos.PODER[i]).append(")\\n");if(bt(p+1,slots,obj,power+Datos.PODER[i],sol,u,log))return true;u[i]=false;sol[p]=-1;log.append("  ".repeat(p)).append("↩ backtrack\\n");}return false;}
  public static String queens(int n){int[]row=new int[n];Arrays.fill(row,-1);StringBuilder s=new StringBuilder("BACKTRACKING — OCHO REINAS\\n");if(placeQueen(0,n,row,s)){for(int r=0;r<n;r++){for(int c=0;c<n;c++)s.append(row[r]==c?" Q ":" · ");s.append("\\n");}s.append("Solución encontrada con retroceso y poda por diagonales.");}return s.toString();}
  private static boolean placeQueen(int r,int n,int[]row,StringBuilder s){if(r==n)return true;for(int c=0;c<n;c++){if(safe(row,r,c)){row[r]=c;if(placeQueen(r+1,n,row,s))return true;row[r]=-1;}}return false;}
  private static boolean safe(int[]row,int r,int c){for(int i=0;i<r;i++)if(row[i]==c||Math.abs(row[i]-c)==r-i)return false;return true;}

  // ---------- Grafos ----------
  private static int weight(int a,int b){if(a==b)return 0;int d=Math.abs(Datos.PODER[a]-Datos.PODER[b]);return d<=300?Math.max(1,d/20):9999;}
  public static int[] vecinos(int u){int[]tmp=new int[Datos.N];int k=0;for(int v=0;v<Datos.N;v++)if(v!=u&&weight(u,v)<9999)tmp[k++]=v;return Arrays.copyOf(tmp,k);}
  public static int[] bfs(int start,StringBuilder log){int n=Datos.N;int[]q=new int[n],order=new int[n];boolean[]v=new boolean[n];int h=0,t=0,k=0;q[t++]=start;v[start]=true;while(h<t){int u=q[h++];order[k++]=u;log.append("Visita: ").append(Datos.NOMBRE[u]).append("\\n");for(int w:vecinos(u))if(!v[w]){v[w]=true;q[t++]=w;}}return Arrays.copyOf(order,k);}
  public static int[] dfs(int start,StringBuilder log){boolean[]v=new boolean[Datos.N];int[]o=new int[Datos.N];int[]k={0};dfsRec(start,v,o,k,log);return Arrays.copyOf(o,k[0]);}
  private static void dfsRec(int u,boolean[]v,int[]o,int[]k,StringBuilder log){v[u]=true;o[k[0]++]=u;log.append("Profundidad: ").append(Datos.NOMBRE[u]).append("\\n");for(int w:vecinos(u))if(!v[w])dfsRec(w,v,o,k,log);}
  public static int[] dijkstra(int start,StringBuilder log){int n=Datos.N;int[]d=new int[n];boolean[]v=new boolean[n];Arrays.fill(d,999999);d[start]=0;for(int it=0;it<n;it++){int u=-1;for(int i=0;i<n;i++)if(!v[i]&&(u<0||d[i]<d[u]))u=i;if(u<0||d[u]==999999)break;v[u]=true;for(int w:vecinos(u)){int nd=d[u]+weight(u,w);if(nd<d[w])d[w]=nd;}}for(int i=0;i<n;i++)log.append(Datos.NOMBRE[i]).append(" = ").append(d[i]==999999?"∞":d[i]).append("\\n");return d;}
  public static int[][] floyd(StringBuilder log){int n=Datos.N;int[][]d=new int[n][n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)d[i][j]=i==j?0:weight(i,j);for(int k=0;k<n;k++)for(int i=0;i<n;i++)for(int j=0;j<n;j++)if(d[i][k]<9999&&d[k][j]<9999)d[i][j]=Math.min(d[i][j],d[i][k]+d[k][j]);log.append("FLOYD-WARSHALL: todos los caminos mínimos. Complejidad O(V³) y espacio O(V²).\\n");return d;}
  public static String mst(String modo){int n=Datos.N;StringBuilder s=new StringBuilder(modo.equals("prim")?"PRIM — ÁRBOL DE RECUBRIMIENTO MÍNIMO\\n":"KRUSKAL — ÁRBOL DE RECUBRIMIENTO MÍNIMO\\n");boolean[]used=new boolean[n];int total=0;List<String>edges=new ArrayList<>();if(modo.equals("prim")){int[]d=new int[n];int[]p=new int[n];Arrays.fill(d,999999);Arrays.fill(p,-1);d[0]=0;for(int it=0;it<n;it++){int u=-1;for(int i=0;i<n;i++)if(!used[i]&&(u<0||d[i]<d[u]))u=i;if(u<0||d[u]>=999999)break;used[u]=true;if(p[u]>=0){total+=d[u];edges.add(Datos.NOMBRE[p[u]]+" — "+Datos.NOMBRE[u]+" = "+d[u]);}for(int v=0;v<n;v++)if(!used[v]&&weight(u,v)<d[v]){d[v]=weight(u,v);p[v]=u;}}}else{List<int[]> es=new ArrayList<>();for(int i=0;i<n;i++)for(int j=i+1;j<n;j++)if(weight(i,j)<9999)es.add(new int[]{weight(i,j),i,j});es.sort(Comparator.comparingInt(e->e[0]));int[]par=new int[n];for(int i=0;i<n;i++)par[i]=i;for(int[]e:es){int a=find(par,e[1]),b=find(par,e[2]);if(a!=b){par[a]=b;total+=e[0];edges.add(Datos.NOMBRE[e[1]]+" — "+Datos.NOMBRE[e[2]]+" = "+e[0]);}}}for(String e:edges)s.append(e).append("\\n");s.append("Costo total: ").append(total).append("\\nComplejidad: Prim O(V²) / Kruskal O(E log E).");return s.toString();}
  private static int find(int[]p,int x){while(p[x]!=x){p[x]=p[p[x]];x=p[x];}return x;}
  public static boolean hasCycle(){int n=Datos.N;int[]p=new int[n];for(int i=0;i<n;i++)p[i]=i;for(int i=0;i<n;i++)for(int j=i+1;j<n;j++)if(weight(i,j)<9999){int a=find(p,i),b=find(p,j);if(a==b)return true;p[a]=b;}return false;}

  // ---------- Branch & Bound ----------
  public static int[] branchAndBound(int presupuesto,StringBuilder log){Integer[]ord=new Integer[Datos.N];for(int i=0;i<Datos.N;i++)ord[i]=i;Arrays.sort(ord,(a,b)->Integer.compare(Datos.PODER[b],Datos.PODER[a]));int[]best={0,0,0};bb(0,presupuesto,0,0,ord,best,0,log);log.append("Mejor poder: ").append(best[0]).append(" con costo ").append(best[1]).append("\\n");List<Integer>sel=new ArrayList<>();for(int pos=0;pos<ord.length;pos++)if((best[2]&(1<<pos))!=0)sel.add(ord[pos]);int[]r=new int[sel.size()];for(int i=0;i<r.length;i++)r[i]=sel.get(i);return r;}
  private static void bb(int pos,int cap,int cost,int power,Integer[]ord,int[]best,int mask,StringBuilder log){if(cost>cap)return;if(pos==ord.length){if(power>best[0]){best[0]=power;best[1]=cost;best[2]=mask;}return;}int upper=power;int rem=cap-cost;for(int j=pos;j<ord.length&&rem>0;j++){int id=ord[j];if(Datos.COSTO[id]<=rem){upper+=Datos.PODER[id];rem-=Datos.COSTO[id];}}if(upper<=best[0]){log.append("PODA en nivel ").append(pos).append(" por cota ").append(upper).append("\\n");return;}int id=ord[pos];bb(pos+1,cap,cost+Datos.COSTO[id],power+Datos.PODER[id],ord,best,mask|(1<<pos),log);bb(pos+1,cap,cost,power,ord,best,mask,log);}

  // ---------- Juegos: Minimax + poda alfa-beta ----------
  public static String minimaxDemo(int depth){int[]nodes={3,5,2,9,12,5,23,23};int score=minimax(nodes,0,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,true);return "MINIMAX + PODA ALFA-BETA\\nProfundidad: "+depth+"\\nValor elegido: "+score+"\\nLa poda elimina ramas cuyo valor ya no puede mejorar la decisión."; }
  private static int minimax(int[]a,int idx,int depth,int alpha,int beta,boolean max){if(depth==0||idx>=a.length)return a[Math.min(idx,a.length-1)];if(max){int best=Integer.MIN_VALUE;for(int k=0;k<2;k++){int val=minimax(a,idx*2+k,depth-1,alpha,beta,false);best=Math.max(best,val);alpha=Math.max(alpha,best);if(beta<=alpha)break;}return best;}else{int best=Integer.MAX_VALUE;for(int k=0;k<2;k++){int val=minimax(a,idx*2+k,depth-1,alpha,beta,true);best=Math.min(best,val);beta=Math.min(beta,best);if(beta<=alpha)break;}return best;}}

  // ---------- Huffman ----------
  public static String huffman(String text,StringBuilder log){int[]f=new int[256];for(char c:text.toCharArray())if(c<256)f[c]++;PriorityQueue<Node>pq=new PriorityQueue<>(Comparator.comparingInt(x->x.freq));for(int i=0;i<256;i++)if(f[i]>0)pq.add(new Node((char)i,f[i],null,null));while(pq.size()>1){Node a=pq.poll(),b=pq.poll();pq.add(new Node('\0',a.freq+b.freq,a,b));}Map<Character,String>codes=new HashMap<>();if(!pq.isEmpty())code(pq.peek(),"",codes);StringBuilder bits=new StringBuilder();for(char c:text.toCharArray())bits.append(codes.get(c));log.append("Huffman: frecuencias, árbol y códigos\\n");for(var e:codes.entrySet())log.append(printChar(e.getKey())).append(" = ").append(e.getValue()).append("\\n");log.append("\\nOriginal: ").append(text.length()*8).append(" bits\\nComprimido: ").append(bits.length()).append(" bits\\nAhorro: ").append(String.format(Locale.US,"%.1f",100.0*(1.0-(double)bits.length()/(text.length()*8)))).append("%\\n");return bits.toString();}
  private static void code(Node n,String p,Map<Character,String>m){if(n.left==null&&n.right==null){m.put(n.ch,p.isEmpty()?"0":p);return;}code(n.left,p+"0",m);code(n.right,p+"1",m);}
  private static String printChar(char c){return c==' '?"[espacio]":c=='\n'?"[salto]":""+c;}
  private record Node(char ch,int freq,Node left,Node right){}

  // ---------- Combate interactivo ----------
  public static int damage(int attacker,int defender,int action,long seed){
    Random r=new Random(seed);
    int base=Datos.PODER[attacker]/5 + r.nextInt(31)-15;
    double mult=action==2?1.65:action==3?0.55:1.0;
    int mitigation=Datos.PODER[defender]/30;
    return Math.max(8,(int)(base*mult)-mitigation);
  }
  public static int aiAction(int hpA,int hpB,int manaA,int manaB,int powerA,int powerB){
    // Minimax sobre acciones abstractas: ataque, especial, defensa.
    int[] choices={1,2,3};int best=1,bestScore=Integer.MIN_VALUE;
    for(int c:choices){if(c==2&&manaA<25)c=1;int self=actionValue(c,powerA,manaA,hpB);int worst=Integer.MAX_VALUE;for(int r:choices){int opp=actionValue(r,powerB,manaB,hpA);worst=Math.min(worst,self-opp); }if(worst>bestScore){bestScore=worst;best=c;}}
    return best;
  }
  private static int actionValue(int action,int power,int mana,int enemyHp){int d=power/5;if(action==2&&mana>=25)d=(int)(d*1.65);if(action==3)d=0;return d+enemyHp/100;}

  public static int[] combate(int a,int b,long seed,StringBuilder log){int va=Datos.VIDA[a],vb=Datos.VIDA[b];Random r=new Random(seed);int round=1;while(va>0&&vb>0&&round<=20){int da=Math.max(20,Datos.PODER[a]/5+r.nextInt(70)-25);int db=Math.max(20,Datos.PODER[b]/5+r.nextInt(70)-25);vb-=da;log.append("Ronda ").append(round).append(": ").append(Datos.NOMBRE[a]).append(" golpea por ").append(da).append(" -> vida rival ").append(Math.max(vb,0)).append("\\n");if(vb<=0)break;va-=db;log.append("          ").append(Datos.NOMBRE[b]).append(" responde por ").append(db).append(" -> vida rival ").append(Math.max(va,0)).append("\\n");round++;}return new int[]{Math.max(va,0),Math.max(vb,0),va>=vb?a:b};}
  public static int suma(int[]ids,int[]v){int s=0;for(int i:ids)s+=v[i];return s;}
  public static String[] names(int[]ids){String[]s=new String[ids.length];for(int i=0;i<ids.length;i++)s[i]=ids[i]>=0?Datos.NOMBRE[ids[i]]:"-";return s;}
}
