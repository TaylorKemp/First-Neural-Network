import java.lang.Math;
/*******************************************************************************
 * The class Matrix supports basic operations on Matrices. It enables a user to 
 * transpose a matrix. It also supports addition of two matrices as well as 
 * scaling them by some factor.
 * 
 * @author tkemp
 *
 */
public class Matrix {
	/* 
	 * A method to sum all of the elements in a one dimensional matrix. It takes 
	 * a matrix of type double with only one column. Method does not modify the
	 * original matrix.
	 * 
	 * @param <matrix> a matrix to sum all of its indices
	 * @return <sum> the sum of all the entries in the matrix
	 */
	public static double matrixSum(double[] matrix){
		double sum = 0.0;//the final sum of all of the entries in the matrix
		try{
		//if matrix is of type null print a stack trace
			for(int i = 0; i < matrix.length; i++){
			//sums all of the elements in matrix
				sum += matrix[i];
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		return sum;
	}
	/* 
	 * A method to transpose a matrix. It will take a double dimensioned array 
	 * and it will switch all of the column indices with the row indices.
	 * Method does not alter original matrix.
	 * 
	 * @param <matrix> the matrix that will be transposed
	 * @return <transposed> the final transposed version of the original matrix
	 */
	public static double[][] transpose(double[][] matrix){
		double[][] transposed = new double[matrix[0].length][matrix.length];
			//the matrix that will be transposed should not be jagged
		try{
		//if rows aren't uniform in length create an exception
			for(int i = 0; i < matrix.length; i++){
			//switches each column index with each row index of the given matrix
				if(matrix[i].length != matrix[0].length){
				//checks that each row is equal to the first row
					throw new IncorrectDimensionException();
				}
				for(int j = 0; j < matrix[i].length; j++){
					transposed[j][i] = matrix[i][j];
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch(IncorrectDimensionException e){
			e.printStackTrace();
		}
		return transposed;
	}
	/* 
	 * A method to multiply two matrices together. Final matrix product will 
	 * have entries such that the row and column have an entry equal to 
	 * that row of the first matrix dotted with that column of the second 
	 * matrix. For example the ith row and jth column of the product matrix 
	 * would be the ith row of the first matrix dotted with the jth column of 
	 * the second matrix. Method does not alter either of the matrices given as
	 * parameters.
	 * 
	 * @param <matrixOne> the first matrix factor.
	 * @param <matrixTwo> the second matrix factor.
	 * @return<matrix> the product matrix of matrixOne and matrixTwo.
	 */
	public static double[] multiply(double[][] matrixOne, double[] matrixTwo){
		double[] matrix = new double[matrixOne.length];//product matrix
		try{
		//takes the product of two matrices if possible
			for(int i = 0; i < matrixOne.length; i++){
			//go over each row in product
				if(matrixTwo.length != matrixOne[i].length){
				//check that matrices can be multiplied
					throw new IncorrectDimensionException();
				}
				for(int j = 0; j < matrixTwo.length; j++){
				//dot row i with the only column in matrixtwo
					matrix[i] += matrixOne[i][j] * matrixTwo[j];
				}
			}
		}catch(IncorrectDimensionException e){
			e.printStackTrace();
		}
		return matrix;
	}
	/* 
	 * A method to multiply every element in a matrix by a scalar. Method does 
	 * not modify the original matrix.
	 * 
	 * @param <matrix> a matrix which will be magnified
	 * @param <scalar> a scalar to multiply each element in the matrix by
	 * @return <finalMatrix> a matrix that stores all of the scaled elements.
	 */
	public static double[] scalarMultiply(double[] matrix, double scalar){
		double[] finalMatrix = new double[matrix.length];
			//will contain all elements in original matrix magnified by a scalar
		for(int i = 0; i < matrix.length; i++){
		//magnifies each element in the matrix
			finalMatrix[i] = matrix[i] * scalar;
		}
		return finalMatrix;
	}
	/* 
	 * A method to add two different matrices together. Matrices must have the
	 * same dimensions to add together. Method does not modify the original matrix.
	 * 
	 * @param <matrixOne> first matrix to add
	 * @param <matrixTwo> second matrix to add
	 * @return <sumMatrix> matrix sum of first and second matrix
	 */
	public static double[] addMatrices(double[] matrixOne, double[] matrixTwo){
		double[] sumMatrix = null;//sum of matrix one and two
		try{
		//add both matrices together
			sumMatrix = new double[matrixOne.length];
			
			if(matrixOne.length != matrixTwo.length){
			//check that both matrices have the same length
				throw new IncorrectDimensionException();
			}
			for(int i = 0; i < matrixOne.length; i++){
			//stores matrixOne and matrixTwo's entry i sum in sumMarix's entry i
				sumMatrix[i] = matrixOne[i] + matrixTwo[i];
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch(IncorrectDimensionException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return sumMatrix;
	}
	/* 
	 * A method to obtain the dot product between two matrices. This method will
	 * find the sum of each element of the first matrix multiplied by the 
	 * corresponding element in the second matrix. This method will not modify 
	 * any of the matrices passed to it.
	 * 
	 * @param <matrixOne> The first matrix to be dotted
	 * @param <matrixTwo> The second matrix to be dotted
	 * @return <dotProduct> this contains the dot product between matrixOne and
	 * matrixTwo.
	 */
	public static double dotMatrices(double[] matrixOne, double[] matrixTwo){
		double dotProduct = 0.0;//holds the dot product
		try{
		//take the dot product between matrixOne and matrixTwo
			if(matrixOne.length != matrixTwo.length){
			//check that they are the same dimension of matrices
				throw new IncorrectDimensionException();
			}
			for(int i = 0; i < matrixOne.length; i++){
				dotProduct += matrixOne[i] * matrixTwo[i];
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch(IncorrectDimensionException e){
			e.printStackTrace();
		}
		return dotProduct;
	}
	/* 
	 * A method to obtain the hadamard product between two matrices. This method 
	 * will find the sum of each element of the first matrix multiplied by the 
	 * corresponding element in the second matrix. This method will not modify 
	 * any of the matrices passed to it.
	 * 
	 * @param <matrixOne> The first matrix to be dotted
	 * @param <matrixTwo> The second matrix to be dotted
	 * @return <dotProduct> this contains the dot product between matrixOne and
	 * matrixTwo.
	 */
	public static double[] hadamardProduct(double[] matrixOne, double[] matrixTwo){
		double[] hadamardProduct = null;//holds the hadamard product
		try{
		//obtain the hadamard product
			hadamardProduct = new double[matrixOne.length];
			
			if(matrixOne.length != matrixTwo.length){
			//check that matrixOnce and matrixTwo are of same dimension
				throw new IncorrectDimensionException();
			}
			for(int i = 0; i < matrixOne.length; i++){
			//take product between each element pair, store in hadamardProduct
				hadamardProduct[i] = matrixOne[i] * matrixTwo[i];
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch(IncorrectDimensionException e){
			e.printStackTrace();
		}
		return hadamardProduct;
	}
}
