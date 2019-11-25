public class SimplexEstrutural {

	private static int linha;
	private static int coluna;
	public static int MenorOuIgual = 1;
	public static int MaiorOuIgual = -1;
	private static double[][] tabela;

	public static void main(String[] args) {

		/*
		Um empresa de eletrodomésticos planeja veicular seus produtos em
		comerciais de TV durante a novela das 8 e os jogos da seleção na Copa.
		Comerciais na novela são vistos por 7 milhões de mulheres e 2 milhões
		de homens e custam $50000.
		Comerciais nos jogos são vistos por 2 milhões de mulheres e 12 milhões
		de homens, e custam $100000.
		Qual a distribuição ideal de comerciais se a empresa deseja que eles sejam
		vistos por 28 milhões de mulheres e 24 milhões de homens a um menor
		custo possível? */

		/*
		Variáveis de decisão:
				* x1 = num. de comerciais veiculados durante a novela.
				* x2 = num. de comerciais veiculados durante os jogos

		Função objetivo:
		Min z = 50x1 + 100x2

		Restrições:
		• Público feminino: 7x1 + 2x2 ≥ 28
		• Público masculino: 2x1 + 12x2 ≥ 24
		• x1, x2 ≥ 0

		Solução ótima: (3.6, 1.4) com z = $320.
*/

		 double[][] Restricao={
		 		 {7, 2, MaiorOuIgual, 28},
				 {2, 12, MenorOuIgual, 24}};

		 double[]maxZ={50,100};

		 montarMatriz(2, 2, Restricao, maxZ);
		 System.out.println();
		SimplexCalculoGeral();
	}

	public static double[][] montarMatriz(int numeroDeVariaveisX, int numeroDeRestricoes, double[][] MatrizRestricao,
			double[] FuncaoObjetiva) {
		linha = numeroDeRestricoes + 1;
		coluna = numeroDeVariaveisX + numeroDeRestricoes + 1;
		tabela = new double[linha][coluna];

		// Adiciona a toda tabela com Zeros
		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++)
				tabela[i][j] = 0;
		}

		// Adiciona a última linha com a função objetiva
		for (int i = 0; i < numeroDeVariaveisX; i++)
			tabela[numeroDeRestricoes][i] = FuncaoObjetiva[i] * (-1);

		// Adiciona o valor das ultima coluna da MatrizRestrição
		for (int i = 0; i < numeroDeRestricoes; i++) {
			tabela[i][coluna - 1] = MatrizRestricao[i][numeroDeVariaveisX + 1];

			// Adiciona os coeficientes das variaveis de folga
			for (int j = 0; j < coluna - 1; j++) {
				if (j == numeroDeVariaveisX)
					tabela[i][j + i] = MatrizRestricao[i][j];

				// Adiciona os coeficientes das variaveis de decisão
				else if (j < numeroDeRestricoes)
					tabela[i][j] = MatrizRestricao[i][j];
			}
		}

		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++) {
				System.out.print(tabela[i][j] + " ");
			}
			System.out.println();
		}

		return tabela;
	}

	public static void SimplexCalculoGeral() {
		int c, l, cont = 0;
		while (ValorOtimoSimplex() == false && cont < linha) {
			c = ColunaPivo();
			l = LinhaPivo(c);
			CalcularPivos(l, c);
			cont++;
		}
		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++) {
				System.out.print(tabela[i][j] + " ");
			}
			System.out.println();
		}
		Imprimir();
	}

	// Verifica qual o valor Otimo
	public static boolean ValorOtimoSimplex() {
		boolean ehOtimo = false;
		int count = 0;
		for (int i = 0; i < coluna; i++) {
			double val = tabela[linha - 1][i];
			if (val >= 0) {
				count++;
			}
		}

		if (count == coluna) {
			ehOtimo = true;
		}

		return ehOtimo;
	}

	// Calcula a matriz
	public static double[][] CalcularPivos(int LinhaPivo, int ColunaPivo) {
		double ValorPivo = tabela[LinhaPivo][ColunaPivo];

		for (int i = 0; i < coluna; i++) {
			tabela[LinhaPivo][i] /= ValorPivo;
		}
		for (int i = 0; i < linha; i++) {
			if (i != LinhaPivo) {
				double c = tabela[i][ColunaPivo];
				for (int j = 0; j < coluna; j++) {
					tabela[i][j] = tabela[i][j] - (c * (tabela[LinhaPivo][j]));
				}

			}
		}

		return tabela;
	}

	// Encontra a Coluna Pivô
	private static int ColunaPivo() {
		double[] valores = new double[coluna];
		int local = 0;

		int pos, count = 0;
		for (pos = 0; pos < coluna - 1; pos++) {
			if (tabela[linha - 1][pos] < 0) {
				count++;
			}
		}

		if (count > 1) {
			for (int i = 0; i < coluna - 1; i++)
				valores[i] = Math.abs(tabela[linha - 1][i]);
			local = MaiorValorNumero(valores);
		} else
			local = count - 1;

		return local;
	}

	// Encontra o menor valor da tabela
	private static int MenorValorNumero(double[] data) {
		double minimum;
		int c, id = 0;
		minimum = data[0];

		for (c = 1; c < data.length; c++) {
			if (data[c] > 0) {
				if (Double.compare(data[c], minimum) < 0) {
					minimum = data[c];
					id = c;
				}
			}
		}

		return id;
	}

	// Encontra a Linha Pivo
	private static int LinhaPivo(int colunaPivo) {
		double[] positivosValores = new double[linha];
		double[] res = new double[linha];
		int QuantidadeValoresNegativos = 0;
		for (int i = 0; i < linha; i++) {
			if (tabela[i][colunaPivo] > 0) {
				positivosValores[i] = tabela[i][colunaPivo];
			} else {
				positivosValores[i] = 0;
				QuantidadeValoresNegativos++;
			}

		}

		if (QuantidadeValoresNegativos == linha)
			System.out.println("Soluções infinitas");
		else {
			for (int i = 0; i < linha; i++) {
				double val = positivosValores[i];
				if (val > 0) {
					res[i] = tabela[i][coluna - 1] / val;
				}
			}
		}

		return MenorValorNumero(res);
	}

	private static int MaiorValorNumero(double[] data) {
		double maximo = 0;
		int c, id = 0;
		maximo = data[0];

		for (c = 1; c < data.length; c++) {
			if (Double.compare(data[c], maximo) > 0) {
				maximo = data[c];
				id = c;
			}
		}

		return id;
	}

	private static void Imprimir() {
		int pos = -1, cont = 0;
		System.out.printf("Z = %.3f\n", tabela[linha - 1][coluna - 1]);

		for (int i = 0; i < coluna - linha; i++) {
			for (int j = 0; j < linha; j++) {
				if (tabela[j][i] == 1)
					pos = j;
				else
					cont++;
			}
			if (cont == linha)
				System.out.printf("X%d=0\n", i + 1);
			else if (pos > -1)
				System.out.printf("X%d = %.3f\n", i + 1, tabela[pos][coluna - 1]);
			pos = -1;
		}
	}
}
