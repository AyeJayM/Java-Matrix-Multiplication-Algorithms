import java.util.Random;

public class algorithm

{
public static void main(String args[])
{
    // This is where I hardcoded the example for the sanity check...
    /*
    
    int[][] matrixONE = { {2, 0, -1,  6},
                          {3, 7,  8,  0}, 
                          {-5, 1, 6,  -2}, 
                          {8,  0,  2, 7} };

    int[][] matrixTWO = { {0, 1, 6,  3},
                          {-2, 8, 7 ,1}, 
                          {2, 0, -1, 0},   
                          {9, 1, 6, -2} };
    */


    
    int n = 4096;
    //This is for creating matrices that are a power of 2

    int[][] matrixONE = new int[n][n];
    int[][] matrixTWO = new int[n][n];

    Random ran = new Random();

    for(int row = 0; row < n ; row++) // Populate Matrix A with Random Integers
    {
        for(int col = 0; col < n; col++)
        {
            matrixONE[row][col] = ran.nextInt();
        }
    }

    for(int row = 0; row < n; row++) // Populate Matrix B with Random Integers
    {
        for(int col = 0; col < n; col++)
        {
            matrixTWO[row][col] = ran.nextInt();
        }
    }
    

    long startTime = System.nanoTime();

    //Calling Classical Matrix Multiplication
    //ClassicalMatrix(matrixONE, matrixTWO);

    //Calling Divide/Conquer Matrix Multiplication
    //DivideNConquerMatrix(matrixONE, matrixTWO);

    //Calling Strassen's Matrix Multiplication
    StrassenStart(matrixONE, matrixTWO);

    long stopTime = System.nanoTime();

    long elapsedTime = stopTime - startTime;

    System.out.println("Total elapsed time in nanoseconds: " + elapsedTime);
    
}



///////////////////////////////////////////



public static void ClassicalMatrix(int[][] matrixA, int[][] matrixB)
{
  

    int[][] result = new int[matrixA.length][matrixA.length];

    for(int i = 0; i < matrixA.length; i++)
    {
        for(int j = 0; j < matrixA.length; j++)
        {
            for(int k = 0; k < matrixA.length; k++)
            {
                result[i][j] = result[i][j] + ( matrixA[i][k] * matrixB[k][j] ); 
            }
        }
    }

    /*
    System.out.println("\nThe results for the matrix using Classical "+ 
                        "Matrix Multiplication are...\n");

    for(int row = 0; row < matrixA.length; row++)
    {
        for(int col = 0; col < matrixA.length; col++)
        {
            System.out.print(result[row][col] + " ");
        }

        System.out.print("\n");
    }

    System.out.print("\n");
    */

}





//////////////////////////////////////////////////////////////





public static void DivideNConquerMatrix(int[][] matrixA, int[][] matrixB)
{
    DivConMatrix(matrixA, matrixB, 0, 0, 0, 0, matrixA.length);

    /*
    int[][] result =

    System.out.println("\nThe results for the matrix using Divide and "+ 
                        "Conquer Matrix Multiplication are...\n");

    for(int row = 0; row < result.length; row++)
    {
        for(int col = 0; col < result.length; col++)
        {
            System.out.print(result[row][col] + " ");
        }

        System.out.print("\n");
    }

    System.out.print("\n");
    */
    
}

public static int[][] DivConMatrix(int[][] matrixA, int[][] matrixB, 
                                    int rowA, int colA, 
                                    int rowB, int colB, int size)
{
    int[][] matrixC = new int[size][size];

    if( size == 1 )
    {
        matrixC[0][0] = matrixA[rowA][colA] * matrixB[rowB][colB];
    }
    else
    {
        int newSize = size / 2;

        //C11 = (A11 * B11) + (A12 * B21)

        add(DivConMatrix(matrixA, matrixB, rowA, colA, rowB, colB, newSize),
            DivConMatrix(matrixA, matrixB, rowA, colA + newSize, rowB + newSize, colB, newSize),
            matrixC, 0, 0);

        //C12 = (A11 * B12) + (A12 * B22)

        add(DivConMatrix(matrixA, matrixB, rowA, colA, rowB, colB + newSize, newSize),
            DivConMatrix(matrixA, matrixB, rowA, colA + newSize, rowB + newSize, colB + newSize, newSize),
            matrixC, 0, newSize);

        //C21 = (A21 * B11) + (A22 * B21)

        add(DivConMatrix(matrixA, matrixB, rowA + newSize, colA, rowB, colB, newSize),
            DivConMatrix(matrixA, matrixB, rowA + newSize, colA + newSize, rowB + newSize, colB, newSize),
            matrixC, newSize, 0);

        //C22 = (A21 * B12) + (A22 * B22)

        add(DivConMatrix(matrixA, matrixB, rowA + newSize, colA, rowB, colB + newSize, newSize),
            DivConMatrix(matrixA, matrixB, rowA + newSize, colA + newSize, rowB + newSize, colB + newSize, newSize),
            matrixC, newSize, newSize);
        

    }

    return matrixC;

}

public static void add(int[][] matrixA, int[][] matrixB, int[][] matrixC, int rowC, int colC)
{
    for (int i = 0; i < matrixA.length; i++)
    {
        for(int j = 0; j < matrixA.length; j++)
        {
            matrixC[i + rowC][j + colC] = matrixA[i][j] + matrixB[i][j];
        }
    }
}



////////////////////////////////////////////

public static void StrassenStart(int[][] matrixA, int[][] matrixB)
{
    
    Strassen(matrixA, matrixB);

    /*
    int[][] result =

    System.out.println("The results for the matrix using Strassen's Matrix "
                + "Multiplication are...\n");
    
    for(int row = 0; row < result.length; row++)
    {
        for(int col = 0; col < result.length; col++)
        {
            System.out.print(result[row][col] + " ");
        }

    System.out.print("\n");

    }

    System.out.print("\n");
    */
}


public static int[][] Strassen(int[][] matrixA, int[][] matrixB)
{
    // Matrix we will return
    int[][] returnedMarix = new int[matrixA.length][matrixA.length];

    //Split the length of our matrix length
    int newSize = matrixA.length/2;
    
    if (matrixA.length == 1)
    {
        returnedMarix[0][0] = matrixA[0][0] * matrixB[0][0];
    }
    else
    {   // Creation of our quadrants
        int[][] A11 = new int[newSize][newSize];
        int[][] A12 = new int[newSize][newSize];
        int[][] A21 = new int[newSize][newSize];
        int[][] A22 = new int[newSize][newSize];

        int[][] B11 = new int[newSize][newSize];
        int[][] B12 = new int[newSize][newSize];
        int[][] B21 = new int[newSize][newSize];
        int[][] B22 = new int[newSize][newSize];

        //Assigning the quadrants their respective values
        StrassenQuad(matrixA, A11, 0, 0);       // UL
        StrassenQuad(matrixA, A12, 0, newSize); // UR
        StrassenQuad(matrixA, A21, newSize, 0); // LL
        StrassenQuad(matrixA, A22, newSize, newSize); //LR

        StrassenQuad(matrixB, B11, 0, 0);       // UL
        StrassenQuad(matrixB, B12, 0, newSize); // UR
        StrassenQuad(matrixB, B21, newSize, 0); // LL
        StrassenQuad(matrixB, B22, newSize, newSize); // LR
        

        //////// P1 - P7 CALCULATIONS ///////

        int[][] P1 = Strassen( StrassenAdd(A11, A22), StrassenAdd(B11, B22) );
        int[][] P2 = Strassen( StrassenAdd(A21, A22), B11 );
        int[][] P3 = Strassen( A11, StrassenSub(B12, B22) );
        int[][] P4 = Strassen( A22, StrassenSub(B21, B11) );
        int[][] P5 = Strassen( StrassenAdd(A11, A12), B22 );
        int[][] P6 = Strassen( StrassenSub(A21, A11), StrassenAdd(B11, B12) );
        int[][] P7 = Strassen( StrassenSub(A12, A22), StrassenAdd(B21, B22) );

        // C CALCULATIONS

        int[][] C11 = StrassenAdd(StrassenSub(StrassenAdd(P1, P4), P5), P7);
        int[][] C12 = StrassenAdd(P3, P5);
        int[][] C21 = StrassenAdd(P2, P4);
        int[][] C22 = StrassenAdd(StrassenSub(StrassenAdd(P1, P3), P2), P6);

        // Now we set our calcualted values to the ReturnedMatrix
        AddToReturn(C11, returnedMarix, 0, 0);
        AddToReturn(C12, returnedMarix, 0, newSize);
        AddToReturn(C21, returnedMarix, newSize, 0);
        AddToReturn(C22, returnedMarix, newSize, newSize);  
    }

    return returnedMarix;

}

public static void StrassenQuad(int[][] matrixA, int[][] matrixB, int rowVal, int colVal)
{
    for(int row = 0, row2 = rowVal; row < matrixB.length; row++, row2++)
    {
        for(int col = 0, col2 = colVal; col < matrixB.length; col++, col2++)
        {
            matrixB[row][col] = matrixA[row2][col2];
        }
    }

}

public static int[][] StrassenAdd(int[][] matrixA, int[][] matrixB)
{
    int[][] addSum = new int[matrixA.length][matrixA.length];

    for(int row = 0; row < matrixA.length; row++)
    {
        for(int col = 0; col < matrixA.length; col++)
        {
            addSum[row][col] = matrixA[row][col] + matrixB[row][col];
        }
    }
    return addSum;
}

public static int[][] StrassenSub(int[][] matrixA, int[][] matrixB)
{
    int[][] subDiff = new int[matrixA.length][matrixA.length];

    for(int row = 0; row < matrixA.length; row++)
    {
        for(int col = 0; col < matrixA.length; col++)
        {
            subDiff[row][col] = matrixA[row][col] - matrixB[row][col];
        }
    }
    return subDiff;
}

public static void AddToReturn(int[][] setMatrix, int[][] returnedMarix, int rowVal, int colVal)
{
    for(int row = 0, row2 = rowVal; row < setMatrix.length; row++, row2++)
    {
        for(int col = 0, col2 = colVal; col < setMatrix.length; col++, col2++)
        {
            returnedMarix[row2][col2] = setMatrix[row][col];
        }
    }   // Since returned Matrix is n * n, we use row2 and col2 for it.
}

}