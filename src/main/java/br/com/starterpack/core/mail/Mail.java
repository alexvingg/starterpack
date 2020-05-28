package br.com.starterpack.core.mail;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Builder
@Data
public class Mail implements Serializable {

    private static final long serialVersionUID = -3910117563587364302L;

    private Optional<String> from;

    private String subject;

    private List<String> to;

    private String body;

    private Optional<Map<String, Object>> data;

    private Optional<List<File>> attachments;

    public static MailBuilder builder() {
        return new MailBuilder(){
            @Override
            public Mail build() {
                prebuild();
                return super.build();
            }
            private void prebuild(){
                this.from(Optional.empty());
                this.data(Optional.empty());
                this.attachments(Optional.empty());
            }
        };
    }

}
