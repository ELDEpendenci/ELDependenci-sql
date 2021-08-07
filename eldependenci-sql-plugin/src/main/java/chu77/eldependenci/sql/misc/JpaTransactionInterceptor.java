package chu77.eldependenci.sql.misc;


import chu77.eldependenci.sql.SQLAddon;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.lang.reflect.Method;

@Deprecated
// just copy and paste and changed a little shit
public class JpaTransactionInterceptor implements MethodInterceptor {

    @Inject
    private Provider<EntityManager> emProvider;

    @Inject
    private SQLAddon sqlAddon;

   // private static final Logger LOGGER = LoggerFactory.getLogger(JpaTransactionInterceptor.class);

    private final ThreadLocal<Boolean> didWeStartWork = new ThreadLocal<>();
    private final ThreadLocal<EntityManager> entityManagerThread = new ThreadLocal<>();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        sqlAddon.getLogger().info("invoking JpaTransactionInterceptor...");
        if (this.entityManagerThread.get() == null) {
            this.entityManagerThread.set(emProvider.get());
            this.didWeStartWork.set(true);
        }

        Transactional transactional = this.readTransactionMetadata(methodInvocation);
        EntityManager entityManager = entityManagerThread.get();
        if (entityManager.getTransaction().isActive()) {
            return methodInvocation.proceed();
        } else {
            EntityTransaction txn = entityManager.getTransaction();
            txn.begin();

            Object result;
            try {
                result = methodInvocation.proceed();
            } catch (Exception var16) {
                if (this.rollbackIfNecessary(transactional, var16, txn)) {
                    txn.commit();
                }

                throw var16;
            } finally {
                if (null != this.didWeStartWork.get() && !txn.isActive()) {
                    this.didWeStartWork.remove();
                    end();
                }

            }

            try {
                txn.commit();
            } finally {
                if (null != this.didWeStartWork.get()) {
                    this.didWeStartWork.remove();
                    end();
                }

            }

            return result;
        }
    }

    private boolean rollbackIfNecessary(Transactional transactional, Exception e, EntityTransaction txn) {
        boolean commit = true;
        Class<? extends Throwable>[] var5 = transactional.rollbackOn();

        for (Class<? extends Throwable> rollBackOn : var5) {
            if (rollBackOn.isInstance(e)) {
                commit = false;
                Class<? extends Throwable>[] var9 = transactional.dontRollbackOn();

                for (Class<? extends Throwable> exceptOn : var9) {
                    if (exceptOn.isInstance(e)) {
                        commit = true;
                        break;
                    }
                }

                if (!commit) {
                    txn.rollback();
                }
                break;
            }
        }

        return commit;
    }

    private Transactional readTransactionMetadata(MethodInvocation methodInvocation) {
        Method method = methodInvocation.getMethod();
        Class<?> targetClass = methodInvocation.getThis().getClass();
        Transactional transactional = method.getAnnotation(Transactional.class);
        if (transactional == null) {
            transactional = targetClass.getAnnotation(Transactional.class);
        }
        if (transactional == null) {
            transactional = JpaTransactionInterceptor.Internal.class.getAnnotation(Transactional.class);
        }
        return transactional;
    }

    private void end() {
        EntityManager em = this.entityManagerThread.get();
        if (null != em) {
            try {
                em.close();
            } finally {
                this.entityManagerThread.remove();
            }

        }
    }


    @Transactional
    private static class Internal {
        private Internal() {
        }
    }
}
