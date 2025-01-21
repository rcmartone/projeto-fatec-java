package br.com.projeto.projeto_fatec.events;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PublicadorDeEvento {
    private final ApplicationEventPublisher publicadorEvento;

    public PublicadorDeEvento(ApplicationEventPublisher publicadorEvento) {
        this.publicadorEvento = publicadorEvento;
    }

    public <T extends ApplicationEvent> void publicarEvento(Class<T> tipoEvento, Object... args)
            throws InstantiationException, IllegalArgumentException {
        Object[] argThis = new Object[args.length + 1];
        argThis[0] = this;
        // Log teste de argThis
        System.out.println(Arrays.toString(argThis));
        // Fim Log teste de argThis
        System.arraycopy(args, 0, argThis, 1, args.length);
        try {

            Constructor<T> construtor = encontrarConstrutor(tipoEvento, argThis);

            if (construtor == null) {
                throw new IllegalArgumentException(
                        "Argumentos enviados não cosrrespondem aos parâmetros de nenhum construtor da classe "
                                + tipoEvento.getName());
            }
            // T evento = construtor.newInstance(argThis);
            T evento = construtor.newInstance(Arrays.copyOfRange(argThis, 0, argThis.length));
            // Log teste de argumentos
            System.out.println("Args enviados para newInstance: " + Arrays.toString(argThis));
            System.out.println("Construtor selecionado: " + construtor);
            // Fim Log Teste
            publicadorEvento.publishEvent(evento);
            System.out.println("Evento publicado: " + tipoEvento.getSimpleName());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new InstantiationException("Não foi possivel instânciar esse evento");
        }
    }

    private <T> Constructor<T> encontrarConstrutor(Class<T> tipoEvento, Object... args) {
        for (Constructor<?> constructor : tipoEvento.getConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == args.length) {
                boolean matches = true;
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!parameterTypes[i].isInstance(args[i])) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    @SuppressWarnings("unchecked")
                    Constructor<T> matchingConstructor = (Constructor<T>) constructor;
                    return matchingConstructor;
                }
            }
        }
        return null;
    }
}
