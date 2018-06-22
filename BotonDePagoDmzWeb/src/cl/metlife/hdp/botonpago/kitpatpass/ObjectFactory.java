
package cl.metlife.hdp.botonpago.kitpatpass;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cl.metlife.hdp.botonpago.kitpatpass package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AcknowledgeTransaction_QNAME = new QName("http://service.wswebpay.webpay.transbank.com/", "acknowledgeTransaction");
    private final static QName _AcknowledgeTransactionResponse_QNAME = new QName("http://service.wswebpay.webpay.transbank.com/", "acknowledgeTransactionResponse");
    private final static QName _GetTransactionResult_QNAME = new QName("http://service.wswebpay.webpay.transbank.com/", "getTransactionResult");
    private final static QName _GetTransactionResultResponse_QNAME = new QName("http://service.wswebpay.webpay.transbank.com/", "getTransactionResultResponse");
    private final static QName _InitTransaction_QNAME = new QName("http://service.wswebpay.webpay.transbank.com/", "initTransaction");
    private final static QName _InitTransactionResponse_QNAME = new QName("http://service.wswebpay.webpay.transbank.com/", "initTransactionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cl.metlife.hdp.botonpago.kitpatpass
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AcknowledgeTransaction }
     * 
     */
    public AcknowledgeTransaction createAcknowledgeTransaction() {
        return new AcknowledgeTransaction();
    }

    /**
     * Create an instance of {@link AcknowledgeTransactionResponse }
     * 
     */
    public AcknowledgeTransactionResponse createAcknowledgeTransactionResponse() {
        return new AcknowledgeTransactionResponse();
    }

    /**
     * Create an instance of {@link GetTransactionResult }
     * 
     */
    public GetTransactionResult createGetTransactionResult() {
        return new GetTransactionResult();
    }

    /**
     * Create an instance of {@link GetTransactionResultResponse }
     * 
     */
    public GetTransactionResultResponse createGetTransactionResultResponse() {
        return new GetTransactionResultResponse();
    }

    /**
     * Create an instance of {@link InitTransaction }
     * 
     */
    public InitTransaction createInitTransaction() {
        return new InitTransaction();
    }

    /**
     * Create an instance of {@link InitTransactionResponse }
     * 
     */
    public InitTransactionResponse createInitTransactionResponse() {
        return new InitTransactionResponse();
    }

    /**
     * Create an instance of {@link WsInitTransactionInput }
     * 
     */
    public WsInitTransactionInput createWsInitTransactionInput() {
        return new WsInitTransactionInput();
    }

    /**
     * Create an instance of {@link WsTransactionDetail }
     * 
     */
    public WsTransactionDetail createWsTransactionDetail() {
        return new WsTransactionDetail();
    }

    /**
     * Create an instance of {@link WsTransactionDetailOutput }
     * 
     */
    public WsTransactionDetailOutput createWsTransactionDetailOutput() {
        return new WsTransactionDetailOutput();
    }

    /**
     * Create an instance of {@link WpmDetailInput }
     * 
     */
    public WpmDetailInput createWpmDetailInput() {
        return new WpmDetailInput();
    }

    /**
     * Create an instance of {@link WsInitTransactionOutput }
     * 
     */
    public WsInitTransactionOutput createWsInitTransactionOutput() {
        return new WsInitTransactionOutput();
    }

    /**
     * Create an instance of {@link TransactionResultOutput }
     * 
     */
    public TransactionResultOutput createTransactionResultOutput() {
        return new TransactionResultOutput();
    }

    /**
     * Create an instance of {@link CardDetail }
     * 
     */
    public CardDetail createCardDetail() {
        return new CardDetail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcknowledgeTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.wswebpay.webpay.transbank.com/", name = "acknowledgeTransaction")
    public JAXBElement<AcknowledgeTransaction> createAcknowledgeTransaction(AcknowledgeTransaction value) {
        return new JAXBElement<AcknowledgeTransaction>(_AcknowledgeTransaction_QNAME, AcknowledgeTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcknowledgeTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.wswebpay.webpay.transbank.com/", name = "acknowledgeTransactionResponse")
    public JAXBElement<AcknowledgeTransactionResponse> createAcknowledgeTransactionResponse(AcknowledgeTransactionResponse value) {
        return new JAXBElement<AcknowledgeTransactionResponse>(_AcknowledgeTransactionResponse_QNAME, AcknowledgeTransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransactionResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.wswebpay.webpay.transbank.com/", name = "getTransactionResult")
    public JAXBElement<GetTransactionResult> createGetTransactionResult(GetTransactionResult value) {
        return new JAXBElement<GetTransactionResult>(_GetTransactionResult_QNAME, GetTransactionResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransactionResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.wswebpay.webpay.transbank.com/", name = "getTransactionResultResponse")
    public JAXBElement<GetTransactionResultResponse> createGetTransactionResultResponse(GetTransactionResultResponse value) {
        return new JAXBElement<GetTransactionResultResponse>(_GetTransactionResultResponse_QNAME, GetTransactionResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.wswebpay.webpay.transbank.com/", name = "initTransaction")
    public JAXBElement<InitTransaction> createInitTransaction(InitTransaction value) {
        return new JAXBElement<InitTransaction>(_InitTransaction_QNAME, InitTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.wswebpay.webpay.transbank.com/", name = "initTransactionResponse")
    public JAXBElement<InitTransactionResponse> createInitTransactionResponse(InitTransactionResponse value) {
        return new JAXBElement<InitTransactionResponse>(_InitTransactionResponse_QNAME, InitTransactionResponse.class, null, value);
    }

}
