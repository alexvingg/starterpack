package br.com.starterpack.core.mail;

public interface IMailProcessor {
    void process(final Mail mail) throws Exception;
}
