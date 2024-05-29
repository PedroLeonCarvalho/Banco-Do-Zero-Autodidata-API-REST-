package com.banking_api.banking_api.dtos.builder;



public class ProdutosBuilder {

    private ProdutosExercicioBuilder produtosExercicioBuilder;

    public ProdutosBuilder( ) {
        this.produtosExercicioBuilder = new ProdutosExercicioBuilder();
    }
public ProdutosBuilder buildId (Long id) {
        this.produtosExercicioBuilder.setId(id);
        return this;
}
public ProdutosExercicioBuilder build() {
      return  this.produtosExercicioBuilder;
}


}


