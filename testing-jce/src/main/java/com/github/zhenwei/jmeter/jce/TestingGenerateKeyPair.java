package com.github.zhenwei.jmeter.jce;

import com.github.zhenwei.jmeter.params.BaseParams;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @author: zhangzhenwei
 * @description: 产生密钥对
 * @date: 2023/5/8  10:07
 * @since: 1.0.0
 */
public class TestingGenerateKeyPair extends AbstractJavaSamplerClient {

  String provider = null, keyAlg = null;
  int keyLength = 256;

  @Override
  public void setupTest(JavaSamplerContext context) {
    provider = context.getParameter(BaseParams.provider);
    keyAlg = context.getParameter(BaseParams.keyAlg);
    keyLength = context.getIntParameter(BaseParams.keyLength);
  }

  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    SampleResult result = new SampleResult();
    result.sampleStart();
    try {
      KeyPairGenerator generator = KeyPairGenerator.getInstance(keyAlg, provider);
      generator.initialize(keyLength);
      KeyPair keyPair = generator.generateKeyPair();
      result.setSuccessful(true);
    } catch (Exception e) {
      result.setSuccessful(false);
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      result.sampleEnd();
    }
    return result;
  }


  @Override
  public Arguments getDefaultParameters() {
    Arguments arguments = new Arguments();
    arguments.addArgument(BaseParams.keyAlg, "SM2");
    arguments.addArgument(BaseParams.keyLength, "256");
    return arguments;
  }
}
