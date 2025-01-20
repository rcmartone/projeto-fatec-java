package br.com.projeto.projeto_fatec.events;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

        try {
            Constructor<T> construtor = encontrarConstrutor(tipoEvento, args);
            if (construtor == null) {
                throw new IllegalArgumentException(
                        "Argumentos enviados não cosrrespondem aos parâmetros de nenhum construtor da classe "
                                + tipoEvento.getName());
            }
            T evento = construtor.newInstance(args);
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
