/*
 * Copyright (c) 2026 GaraKrral
 *
 * Source code in this project is licensed under the GNU General Public License v3.0 (GPLv3).
 * See the LICENSE file for details.
 *
 * All game assets, including but not limited to graphics, audio, models, textures,
 * and other non-code content, are proprietary and All Rights Reserved unless
 * explicitly stated otherwise.
 *
 */

package mc.garakrral.geologica.util;

import mc.garakrral.geologica.Geologica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Centralized logging utility used throughout Geologica.
 *
 * <p>
 * This class provides a lightweight abstraction layer over SLF4J and is
 * intended to be the primary logging entry point for all internal systems,
 * including registration, world generation, data generation, networking,
 * configuration loading, compatibility checks, serialization, resource
 * processing, bootstrap diagnostics, and future subsystems.
 * </p>
 *
 * <p>
 * The utility supports both caller-resolved logging and explicit class-based
 * logging. In caller-resolved mode, the calling class is discovered through a
 * shared {@link StackWalker} instance, which keeps call sites concise while
 * still preserving useful logger ownership in the output. In explicit mode,
 * the caller may supply a class reference directly when the log source should
 * be tied to a specific subsystem regardless of where the call originated.
 * </p>
 *
 * <p>
 * All standard SLF4J log levels are supported: {@code TRACE}, {@code DEBUG},
 * {@code INFO}, {@code WARN}, and {@code ERROR}. For each level, the utility
 * exposes multiple overloads so the caller can choose the most appropriate
 * logging style for the situation:
 * </p>
 *
 * <ul>
 *     <li>plain string messages for direct output;</li>
 *     <li>object logging for quick diagnostics and registry inspection;</li>
 *     <li>format-string logging with varargs for structured messages;</li>
 *     <li>throwable-aware overloads for recording stack traces;</li>
 *     <li>class-based overloads for explicit logger ownership.</li>
 * </ul>
 *
 * <p>
 * The formatting overloads delegate directly to SLF4J parameter substitution,
 * meaning placeholders such as {@code {}} are resolved lazily by the logging
 * backend. This keeps logging efficient and avoids unnecessary string building
 * when a log level is disabled.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>{@code
 * LogUtil.info("Loading world generation");
 * LogUtil.info("Registered {} blocks and {} items", blockCount, itemCount);
 * LogUtil.info(GeologicaWorldGen.BUILDER);
 *
 * LogUtil.debug(GeologicaDataGenerator.class, "Datagen provider: {}", provider);
 * LogUtil.warn("Missing optional resource: {}", resourceId);
 * LogUtil.error("Datagen initialization failed", exception);
 * }</pre>
 *
 * <p>
 * This class is intentionally non-instantiable and exists solely as a
 * collection of reusable static helper methods.
 * </p>
 *
 * @since 0.1.0
 */
public final class LogUtil {
    /**
     * Shared {@link StackWalker} instance used to resolve the calling class.
     *
     * <p>
     * This instance is reused for all caller-resolved logging operations to
     * avoid repeatedly allocating new {@code StackWalker} objects.
     * </p>
     */
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Cached logger instances keyed by the owning class.
     *
     * <p>
     * This cache avoids repeating logger lookups for classes that log often.
     * While most SLF4J implementations also cache logger instances internally,
     * keeping a small local cache makes logger access consistent and centralizes
     * ownership resolution in one place.
     * </p>
     */
    private static final Map<Class<?>, Logger> LOGGER_CACHE = new ConcurrentHashMap<>();

    /**
     * Constructs a new utility instance.
     *
     * <p>
     * This constructor is intentionally inaccessible because this class only
     * contains static utility methods.
     * </p>
     *
     * @throws UnsupportedOperationException always
     */
    private LogUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Logs the Geologica startup banner.
     *
     * <p>
     * This method is intended to be called during mod initialization and
     * outputs a friendly startup message containing the currently loaded
     * Geologica version.
     * </p>
     *
     * <p>
     * This is a convenience overload and delegates to the caller-resolved
     * informational logger.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info();
     * }</pre>
     */
    public static void info() {
        info("Say hi for rock 'n' rocks! You are using Geologica %s".formatted(Geologica.VERSION));
    }

    /**
     * Logs a trace message using the caller's logger.
     *
     * <p>
     * Trace messages are the most verbose level and are intended for extremely
     * detailed diagnostics, deep troubleshooting, and control-flow investigation
     * when debugging difficult issues.
     * </p>
     *
     * <p>
     * This overload automatically resolves the calling class and forwards the
     * message to the underlying SLF4J logger at the {@code TRACE} level.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.trace("Entering worldgen bootstrap");
     * }</pre>
     *
     * @param message the message to log
     *
     * @see #trace(Class, String)
     * @see #trace(String, Throwable)
     * @see #trace(String, Object...)
     */
    public static void trace(String message) {
        getCallerLogger().trace(message);
    }

    /**
     * Logs a trace message using the specified class logger.
     *
     * <p>
     * This overload is useful when the logger source should be explicit rather
     * than derived from the calling context.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.trace(GeologicaWorldGen.class, "Resolving configured feature");
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     *
     * @see #trace(String)
     * @see #trace(Class, String, Throwable)
     * @see #trace(Class, String, Object...)
     */
    public static void trace(Class<?> clazz, String message) {
        getLogger(clazz).trace(message);
    }

    /**
     * Logs a trace message together with an associated exception.
     *
     * <p>
     * This overload should be used when trace-level output is needed alongside
     * a stack trace, typically for deep diagnostics or internal debugging.
     * </p>
     *
     * <p>
     * The throwable is passed directly to SLF4J so the full exception chain is
     * preserved.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.trace("Detailed worldgen failure", exception);
     * }</pre>
     *
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #trace(String)
     * @see #trace(Class, String, Throwable)
     * @see #trace(String, Object...)
     */
    public static void trace(String message, Throwable throwable) {
        getCallerLogger().trace(message, throwable);
    }

    /**
     * Logs a trace message together with an associated exception using the
     * specified class logger.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.trace(GeologicaWorldGen.class, "Could not resolve feature", exception);
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #trace(String, Throwable)
     * @see #trace(Class, String)
     * @see #trace(Class, String, Object...)
     */
    public static void trace(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).trace(message, throwable);
    }

    /**
     * Logs a trace message using the caller's logger and SLF4J formatting.
     *
     * <p>
     * This overload is intended for structured trace output that should use
     * SLF4J-style placeholder substitution instead of manual string building.
     * Placeholder arguments are passed directly to the logging backend and are
     * evaluated lazily, which avoids unnecessary string construction when trace
     * logging is disabled.
     * </p>
     *
     * <p>
     * The message may contain one or more {@code {}} placeholders which will be
     * replaced in order by the supplied arguments.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.trace("Resolved feature {} at {}", featureId, pos);
     * }</pre>
     *
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #trace(String)
     * @see #trace(String, Throwable)
     * @see #trace(Class, String, Object...)
     */
    public static void trace(String message, Object... args) {
        getCallerLogger().trace(message, args);
    }

    /**
     * Logs a trace message using the specified class logger and SLF4J formatting.
     *
     * @param clazz the logger owner
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #trace(Class, String)
     * @see #trace(Class, String, Throwable)
     * @see #trace(String, Object...)
     */
    public static void trace(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).trace(message, args);
    }

    /**
     * Logs a trace representation of the supplied object using the caller's
     * logger.
     *
     * <p>
     * The supplied object is converted using {@link String#valueOf(Object)} and
     * logged using the {@code TRACE} level.
     * </p>
     *
     * @param object the object to log
     *
     * @see #trace(String)
     * @see #trace(Class, Object)
     */
    public static void trace(Object object) {
        getCallerLogger().trace(String.valueOf(object));
    }

    /**
     * Logs a trace representation of the supplied object using the specified
     * class logger.
     *
     * @param clazz the logger owner
     * @param object the object to log
     *
     * @see #trace(Object)
     * @see #trace(Class, String)
     */
    public static void trace(Class<?> clazz, Object object) {
        getLogger(clazz).trace(String.valueOf(object));
    }

    /**
     * Logs a debug message using the caller's logger.
     *
     * <p>
     * Debug messages are intended for development-time diagnostics and are
     * typically used for control flow, state inspection, temporary validation,
     * and other information that is useful while actively building or testing
     * the mod.
     * </p>
     *
     * <p>
     * This overload automatically resolves the calling class and forwards the
     * message to the underlying SLF4J logger at the {@code DEBUG} level.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.debug("Registering block items");
     * }</pre>
     *
     * @param message the message to log
     *
     * @see #debug(Class, String)
     * @see #debug(String, Throwable)
     * @see #debug(String, Object...)
     */
    public static void debug(String message) {
        getCallerLogger().debug(message);
    }

    /**
     * Logs a debug message using the specified class logger.
     *
     * <p>
     * This overload is useful when the log source should be tied to a known
     * class rather than the automatic caller resolution mechanism.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.debug(RegistrationUtil.class, "Adding block to main tab");
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     *
     * @see #debug(String)
     * @see #debug(Class, String, Throwable)
     * @see #debug(Class, String, Object...)
     */
    public static void debug(Class<?> clazz, String message) {
        getLogger(clazz).debug(message);
    }

    /**
     * Logs a debug message together with an associated exception.
     *
     * <p>
     * This overload is appropriate when a recoverable or expected diagnostic
     * condition also produced an exception that should be recorded for later
     * inspection.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.debug("Optional cache entry failed to load", exception);
     * }</pre>
     *
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #debug(String)
     * @see #debug(Class, String, Throwable)
     * @see #debug(String, Object...)
     */
    public static void debug(String message, Throwable throwable) {
        getCallerLogger().debug(message, throwable);
    }

    /**
     * Logs a debug message together with an associated exception using the
     * specified class logger.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.debug(GeologicaDataGenerator.class, "Data provider failed", exception);
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #debug(String, Throwable)
     * @see #debug(Class, String)
     * @see #debug(Class, String, Object...)
     */
    public static void debug(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).debug(message, throwable);
    }

    /**
     * Logs a debug message using the caller's logger and SLF4J formatting.
     *
     * <p>
     * The message may contain {@code {}} placeholders which will be filled by the
     * supplied arguments in order. The arguments are passed directly to SLF4J so
     * formatting is evaluated lazily and only when the debug level is enabled.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.debug("Registered {} blocks and {} items", blocks, items);
     * }</pre>
     *
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #debug(String)
     * @see #debug(String, Throwable)
     * @see #debug(Class, String, Object...)
     */
    public static void debug(String message, Object... args) {
        getCallerLogger().debug(message, args);
    }

    /**
     * Logs a debug message using the specified class logger and SLF4J formatting.
     *
     * @param clazz the logger owner
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #debug(Class, String)
     * @see #debug(Class, String, Throwable)
     * @see #debug(String, Object...)
     */
    public static void debug(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).debug(message, args);
    }

    /**
     * Logs a debug representation of the supplied object using the caller's
     * logger.
     *
     * <p>
     * This overload is primarily intended for development diagnostics where an
     * object's textual representation should be emitted without manually calling
     * {@code toString()}.
     * </p>
     *
     * <p>
     * The supplied object is converted using {@link String#valueOf(Object)} and
     * logged using the {@code DEBUG} level.
     * </p>
     *
     * @param object the object to log
     *
     * @see #debug(String)
     * @see #debug(Class, Object)
     */
    public static void debug(Object object) {
        getCallerLogger().debug(String.valueOf(object));
    }

    /**
     * Logs a debug representation of the supplied object using the specified
     * class logger.
     *
     * <p>
     * This method is useful when diagnostic output should clearly belong to a
     * specific subsystem or utility class.
     * </p>
     *
     * @param clazz the logger owner
     * @param object the object to log
     *
     * @see #debug(Object)
     * @see #debug(Class, String)
     */
    public static void debug(Class<?> clazz, Object object) {
        getLogger(clazz).debug(String.valueOf(object));
    }

    /**
     * Logs an informational message using the caller's logger.
     *
     * <p>
     * Informational messages should be used for meaningful runtime events that
     * are expected during normal execution, such as initialization progress,
     * registration milestones, configuration loading, or other user-visible
     * state transitions.
     * </p>
     *
     * <p>
     * This overload automatically resolves the calling class and forwards the
     * message to the underlying SLF4J logger at the {@code INFO} level.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info("Loading world generation");
     * }</pre>
     *
     * @param message the message to log
     *
     * @see #info(Class, String)
     * @see #info(String, Throwable)
     * @see #info(String, Object...)
     */
    public static void info(String message) {
        getCallerLogger().info(message);
    }

    /**
     * Logs an informational message using the specified class logger.
     *
     * <p>
     * The provided class is used as the logger source instead of the
     * automatically detected caller.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info(MyClass.class, "Loading world generation");
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     *
     * @see #info(String)
     * @see #info(Class, String, Throwable)
     * @see #info(Class, String, Object...)
     */
    public static void info(Class<?> clazz, String message) {
        getLogger(clazz).info(message);
    }

    /**
     * Logs an informational message together with an associated exception.
     *
     * <p>
     * Although informational logging is usually not tied to failures, this
     * overload is useful when a handled exception still deserves to be
     * recorded without escalating the event to warning or error level.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info("Using fallback configuration", exception);
     * }</pre>
     *
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #info(String)
     * @see #info(Class, String, Throwable)
     * @see #info(String, Object...)
     */
    public static void info(String message, Throwable throwable) {
        getCallerLogger().info(message, throwable);
    }

    /**
     * Logs an informational message together with an associated exception using
     * the specified class logger.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info(GeologicaDataGenerator.class, "Datagen fallback activated", exception);
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #info(String, Throwable)
     * @see #info(Class, String)
     * @see #info(Class, String, Object...)
     */
    public static void info(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).info(message, throwable);
    }

    /**
     * Logs an informational message using the caller's logger and SLF4J formatting.
     *
     * <p>
     * This overload is especially useful for registration summaries, feature
     * counts, resource names, and other runtime values that should be inserted
     * into a log line lazily using SLF4J placeholder substitution.
     * </p>
     *
     * <p>
     * The message may contain one or more {@code {}} placeholders. The supplied
     * arguments are passed directly to SLF4J and will only be formatted if the
     * {@code INFO} level is enabled.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info("Registered {} blocks, {} items, and {} tabs", blocks, items, tabs);
     * }</pre>
     *
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #info(String)
     * @see #info(String, Throwable)
     * @see #info(Class, String, Object...)
     */
    public static void info(String message, Object... args) {
        getCallerLogger().info(message, args);
    }

    /**
     * Logs an informational message using the specified class logger and SLF4J formatting.
     *
     * @param clazz the logger owner
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #info(Class, String)
     * @see #info(Class, String, Throwable)
     * @see #info(String, Object...)
     */
    public static void info(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).info(message, args);
    }

    /**
     * Logs an informational representation of the supplied object using the
     * caller's logger.
     *
     * <p>
     * This overload is a convenience method intended for quick diagnostics,
     * development logging, registry inspection, and debugging tasks where an
     * object instance should be logged directly without manually converting it
     * into a string.
     * </p>
     *
     * <p>
     * Internally, the supplied object is converted using
     * {@link String#valueOf(Object)}. This ensures that {@code null} values are
     * safely logged as the literal string {@code "null"} rather than causing a
     * {@link NullPointerException}.
     * </p>
     *
     * <p>
     * The resulting text is then forwarded to the underlying SLF4J logger using
     * the {@code INFO} level.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info(GeologicaWorldGen.BUILDER);
     *
     * LogUtil.info(blockState);
     *
     * LogUtil.info(registry);
     * }</pre>
     *
     * @param object the object to log
     *
     * @see #info(String)
     * @see #info(Class, Object)
     */
    public static void info(Object object) {
        getCallerLogger().info(String.valueOf(object));
    }

    /**
     * Logs an informational representation of the supplied object using the
     * specified class logger.
     *
     * <p>
     * This overload is useful when log ownership should be explicitly associated
     * with a known class rather than being automatically resolved from the caller.
     * </p>
     *
     * <p>
     * The supplied object is converted using {@link String#valueOf(Object)} and
     * logged at the {@code INFO} level.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info(
     *         GeologicaDataGenerator.class,
     *         GeologicaWorldGen.BUILDER
     * );
     * }</pre>
     *
     * @param clazz the logger owner
     * @param object the object to log
     *
     * @see #info(Object)
     * @see #info(Class, String)
     */
    public static void info(Class<?> clazz, Object object) {
        getLogger(clazz).info(String.valueOf(object));
    }

    /**
     * Logs a warning message using the caller's logger.
     *
     * <p>
     * Warning messages should be used for non-fatal situations where execution
     * can continue safely but something unexpected, unusual, or potentially
     * problematic occurred.
     * </p>
     *
     * <p>
     * This overload automatically resolves the calling class and forwards the
     * message to the underlying SLF4J logger at the {@code WARN} level.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.warn("Missing optional resource");
     * }</pre>
     *
     * @param message the message to log
     *
     * @see #warn(Class, String)
     * @see #warn(String, Throwable)
     * @see #warn(String, Object...)
     */
    public static void warn(String message) {
        getCallerLogger().warn(message);
    }

    /**
     * Logs a warning message using the specified class logger.
     *
     * <p>
     * This overload is useful when the warning should clearly be associated
     * with a particular subsystem or utility class.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.warn(WorldGen.class, "Feature was skipped");
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     *
     * @see #warn(String)
     * @see #warn(Class, String, Throwable)
     * @see #warn(Class, String, Object...)
     */
    public static void warn(Class<?> clazz, String message) {
        getLogger(clazz).warn(message);
    }

    /**
     * Logs a warning message together with an associated exception.
     *
     * <p>
     * This overload should be preferred whenever a recoverable problem was
     * caused by an exception and the stack trace may help diagnose the issue.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.warn("Optional config file could not be read", exception);
     * }</pre>
     *
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #warn(String)
     * @see #warn(Class, String, Throwable)
     * @see #warn(String, Object...)
     */
    public static void warn(String message, Throwable throwable) {
        getCallerLogger().warn(message, throwable);
    }

    /**
     * Logs a warning message together with an associated exception using the
     * specified class logger.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.warn(RegistrationUtil.class, "Block registration fallback used", exception);
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #warn(String, Throwable)
     * @see #warn(Class, String)
     * @see #warn(Class, String, Object...)
     */
    public static void warn(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).warn(message, throwable);
    }

    /**
     * Logs a warning message using the caller's logger and SLF4J formatting.
     *
     * <p>
     * Warning formatting is useful for recoverable problems where you still want
     * to include runtime values such as registry names, file paths, or fallback
     * selections without eagerly building the string yourself.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.warn("Using fallback entry for {}", resourceId);
     * }</pre>
     *
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #warn(String)
     * @see #warn(String, Throwable)
     * @see #warn(Class, String, Object...)
     */
    public static void warn(String message, Object... args) {
        getCallerLogger().warn(message, args);
    }

    /**
     * Logs a warning message using the specified class logger and SLF4J formatting.
     *
     * @param clazz the logger owner
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #warn(Class, String)
     * @see #warn(Class, String, Throwable)
     * @see #warn(String, Object...)
     */
    public static void warn(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).warn(message, args);
    }

    /**
     * Logs a warning representation of the supplied object using the caller's
     * logger.
     *
     * <p>
     * The supplied object is converted using
     * {@link String#valueOf(Object)} and logged using the
     * {@code WARN} level.
     * </p>
     *
     * @param object the object to log
     *
     * @see #warn(String)
     * @see #warn(Class, Object)
     */
    public static void warn(Object object) {
        getCallerLogger().warn(String.valueOf(object));
    }

    /**
     * Logs a warning representation of the supplied object using the specified
     * class logger.
     *
     * @param clazz the logger owner
     * @param object the object to log
     *
     * @see #warn(Object)
     * @see #warn(Class, String)
     */
    public static void warn(Class<?> clazz, Object object) {
        getLogger(clazz).warn(String.valueOf(object));
    }

    /**
     * Logs an error message using the caller's logger.
     *
     * <p>
     * Error messages indicate that an operation failed, could not be completed,
     * or reached a state that likely requires developer attention.
     * </p>
     *
     * <p>
     * This overload automatically resolves the calling class and forwards the
     * message to the underlying SLF4J logger at the {@code ERROR} level.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.error("Failed to register block");
     * }</pre>
     *
     * @param message the message to log
     *
     * @see #error(Class, String)
     * @see #error(String, Throwable)
     * @see #error(String, Object...)
     */
    public static void error(String message) {
        getCallerLogger().error(message);
    }

    /**
     * Logs an error message using the specified class logger.
     *
     * <p>
     * This overload is useful when the origin of the failure should be explicit
     * in the logger name rather than inferred from the caller.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.error(MyClass.class, "Unexpected exception");
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     *
     * @see #error(String)
     * @see #error(Class, String, Throwable)
     * @see #error(Class, String, Object...)
     */
    public static void error(Class<?> clazz, String message) {
        getLogger(clazz).error(message);
    }

    /**
     * Logs an error message together with an associated exception.
     *
     * <p>
     * This overload should be used whenever a failure was caused by an
     * exception and the full stack trace is needed for debugging or crash
     * analysis.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.error("Datagen initialization failed", exception);
     * }</pre>
     *
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #error(String)
     * @see #error(Class, String, Throwable)
     * @see #error(String, Object...)
     */
    public static void error(String message, Throwable throwable) {
        getCallerLogger().error(message, throwable);
    }

    /**
     * Logs an error message together with an associated exception using the
     * specified class logger.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.error(GeologicaDataGenerator.class, "Datagen initialization failed", exception);
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     * @param throwable the associated exception
     *
     * @see #error(String, Throwable)
     * @see #error(Class, String)
     * @see #error(Class, String, Object...)
     */
    public static void error(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).error(message, throwable);
    }

    /**
     * Logs an error message using the caller's logger and SLF4J formatting.
     *
     * <p>
     * Error formatting is useful when reporting failures that still benefit from
     * structured runtime data, such as registry keys, paths, identifiers,
     * exception context, or partially resolved state.
     * </p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.error("Failed to register {} because of {}", entryId, reason);
     * }</pre>
     *
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #error(String)
     * @see #error(String, Throwable)
     * @see #error(Class, String, Object...)
     */
    public static void error(String message, Object... args) {
        getCallerLogger().error(message, args);
    }

    /**
     * Logs an error message using the specified class logger and SLF4J formatting.
     *
     * @param clazz the logger owner
     * @param message the format message to log
     * @param args the arguments to insert into the format message
     *
     * @see #error(Class, String)
     * @see #error(Class, String, Throwable)
     * @see #error(String, Object...)
     */
    public static void error(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).error(message, args);
    }

    /**
     * Logs an error representation of the supplied object using the caller's
     * logger.
     *
     * <p>
     * The supplied object is converted using
     * {@link String#valueOf(Object)} and logged using the
     * {@code ERROR} level.
     * </p>
     *
     * @param object the object to log
     *
     * @see #error(String)
     * @see #error(Class, Object)
     */
    public static void error(Object object) {
        getCallerLogger().error(String.valueOf(object));
    }

    /**
     * Logs an error representation of the supplied object using the specified
     * class logger.
     *
     * @param clazz the logger owner
     * @param object the object to log
     *
     * @see #error(Object)
     * @see #error(Class, String)
     */
    public static void error(Class<?> clazz, Object object) {
        getLogger(clazz).error(String.valueOf(object));
    }

    /**
     * Returns a cached logger for the specified class.
     *
     * <p>
     * This method is used internally by the explicit class-based logging
     * overloads. The returned logger is cached for the lifetime of the
     * application to avoid repeated lookup work.
     * </p>
     *
     * @param clazz the logger owner
     * @return a cached logger instance
     */
    private static Logger getLogger(Class<?> clazz) {
        return LOGGER_CACHE.computeIfAbsent(clazz, LoggerFactory::getLogger);
    }

    /**
     * Resolves the logger associated with the calling class.
     *
     * <p>
     * This method is used internally by the automatic caller-resolved logging
     * overloads and should not normally be called directly.
     * </p>
     *
     * @return the caller logger instance
     */
    private static Logger getCallerLogger() {
        return getLogger(STACK_WALKER.getCallerClass());
    }
}