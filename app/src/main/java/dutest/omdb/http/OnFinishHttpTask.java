package dutest.omdb.http;

/**
 * Created by Pedreduardo on 24/05/2016.
 */
public interface OnFinishHttpTask {
    /**
     * Função responsável levar o objeto de resposta de uma requisição à classe que iniciou a requisição.
     * @param result resultado da requisiçao
     * @param method GET ou POST
     */
    public void onFinishTask(Object result, String method);
}
