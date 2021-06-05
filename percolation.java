import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;

public class Percolation{
    private int[] id;
    private int[] state;
    private int[] sz;
    private int N;
    private int count_open = 0;

    //creates n by n grid,with all sides initially blocked.
    public Percolation(int n)throws IllegalArgumentException
    {
        if(n<=0)
            throw new IllegalArgumentException("n less than or equal zero");
        id = new int[n*n+2];
        sz = new int[n*n+2];
        state = new int[n*n+2];
        N = n;
        for(int i= 0; i< n*n+2; i++){
            id[i] = i;
            sz[i] = 1;
            state[i] = 0;// state = 0 for closed and state = 1 for open
        }
        state[n*n] = 1;//virtual top
        state[n*n+1] = 1;//virtual bottom
    }

    private int idx(int row, int col) {
        if (validate(row, col))
            return (row - 1) * N + col - 1;
        else {
            return -1;
        }
    }

    //throwing error
    private void error_throw(int idx) throws IllegalArgumentException{
        if(idx == -1)
            throw new IllegalArgumentException("value out of range");
    }

    //validation of corner cases
    private boolean validate(int row, int col){
        return (row >= 1 && row <= N) && (col >=1 && col <=N);
    }

    //opens the site(row,col) if its not open already
    public void open(int row, int col){
        int index = idx(row,col);
        //System.out.println(index);
        error_throw(index);
        if(state[index] == 0){
            state[index] = 1;
            count_open++;

            int[] _idx = new int[4];

            _idx[0] = idx(row-1,col);
            _idx[1] = idx(row+1,col);
            _idx[2] = idx(row,col+1);
            _idx[3] = idx(row,col-1);

            for(int i = 0; i < 4; i++){
                if(_idx[i]!= -1 && state[_idx[i]] == 1) {
                    union(index,_idx[i]);
                }
            }
        }
    }

    //is the site(row,col) is open?
    public boolean isOpen(int row, int col){
        error_throw(idx(row,col));
        return state[idx(row,col)] == 1;
    }

    //is the site (row,col) full?
    public boolean isFull(int row, int col){
        error_throw(idx(row, col));
        return state[idx(row,col)] == 0;
    }

    //returns the number of open sites
    public int numberOfOpenSites(){
        return count_open;
    }

    //finding the root
    private int root(int n){
        while(id[n] != n ){
            id[n] = id[id[n]];
            n = id[n];
        }
        return n;
    }

    //union function
    private void union(int p, int q){
        int root_p = root(p);
        int root_q = root(q);
        if (root_p == root_q) return;
        if (sz[root_p] > sz[root_q]) {id[root_q] = root_p; sz[root_p] += sz[root_q];}
        else {id[root_p] = root_q; sz[root_q] += sz[root_p];}
    }

    //virtual points connection
    private void virtual_points() {

        for (int i = 1; i <= N; i++) {
            if (isOpen(1,i))
                union(N*N,idx(1,i));
        }
        for(int i=1;i <= N;i++){
            if(isOpen(N,i))
                union(N*N+1, idx(N,i));
        }
    }
    //checking if the system percolates
    public boolean percolates(){
        virtual_points();
        return root(N*N) == root(N*N+1);
    }

    //main function
    public static void main(String[] args){
        int input = StdIn.readInt();
        Percolation P = new Percolation(input);
        while(!P.percolates()){
            int row = StdRandom.uniform(1,input+1);
            int col = StdRandom.uniform(1,input+1);
            P.open(row,col);
            //System.out.println("System opening"+ P.numberOfOpenSites()+"\n");
        }
        System.out.println("System percolated after opening"+ P.numberOfOpenSites()+"\n");
    }
}
