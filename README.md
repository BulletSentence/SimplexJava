# Simplex Almeida
 Simplex para resolver questões de Pesquisa Operacional relacionadas à materia de Programação Linear
 
 - Exemplo -
Um empresa de eletrodomésticos planeja veicular seus produtos em
comerciais de TV durante a novela das 8 e os jogos da seleção na Copa.
Comerciais na novela são vistos por 7 milhões de mulheres e 2 milhões
de homens e custam $50000.
Comerciais nos jogos são vistos por 2 milhões de mulheres e 12 milhões
de homens, e custam $100000.
Qual a distribuição ideal de comerciais se a empresa deseja que eles sejam
vistos por 28 milhões de mulheres e 24 milhões de homens a um menor
custo possível?
  
 		 Variáveis de decisão:
		* x1 = num. de comerciais veiculados durante a novela
		* x2 = num. de comerciais veiculados durante os jogos

		Função objetivo:
		 * Min z = 50x1 + 100x2

		Restrições:
		 • Público feminino: 7x1 + 2x2 ≥ 28
		 • Público masculino: 2x1 + 12x2 ≥ 24
		 • x1, x2 ≥ 0

		Solução ótima: (3.6, 1.4) com z = $320.

		- Exemplo no Código - 
		Restricao={
		{7, 2, MaiorOuIgual, 28},
		{2, 12, MaiorOuIgual, 24}};

		maxZ={50,100};
