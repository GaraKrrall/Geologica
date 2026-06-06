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

/**
 * Utility class providing simplified logging methods for Geologica.
 *
 * <p>This class acts as a lightweight wrapper around SLF4J and automatically
 * resolves the calling class when a logger target is not explicitly specified.</p>
 *
 * <p>Unlike manually creating a logger for every class, this utility allows
 * messages to be logged with a single method call while still preserving the
 * correct logger source.</p>
 *
 * <p>The utility supports three log levels:</p>
 *
 * <ul>
 *     <li>{@link #info(String)} - Informational messages.</li>
 *     <li>{@link #warn(String)} - Warning messages.</li>
 *     <li>{@link #error(String)} - Error messages.</li>
 * </ul>
 *
 * <p>Messages may also be logged using an explicit class reference:</p>
 *
 * <pre>{@code
 * LogUtil.info(MyClass.class, "Hello World");
 * LogUtil.warn(MyClass.class, "Something looks suspicious");
 * LogUtil.error(MyClass.class, "Something failed");
 * }</pre>
 *
 * <p>Or by automatically resolving the caller:</p>
 *
 * <pre>{@code
 * LogUtil.info("Loading configuration");
 * LogUtil.warn("Configuration value is deprecated");
 * LogUtil.error("Failed to load configuration");
 * }</pre>
 *
 * <p>This class cannot be instantiated.</p>
 *
 */
public final class LogUtil {
    /**
     * Shared {@link StackWalker} instance used to resolve the calling class.
     *
     * <p>This instance is reused for all logging operations to avoid repeatedly
     * allocating new {@code StackWalker} objects.</p>
     */
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Constructs a new utility instance.
     *
     * <p>This constructor is intentionally inaccessible because this class only
     * contains static utility methods.</p>
     *
     * @throws UnsupportedOperationException always
     */
    private LogUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Logs the Geologica startup banner.
     *
     * <p>This method is intended to be called during mod initialization and
     * outputs a friendly startup message containing the currently loaded
     * Geologica version.</p>
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
     * Logs an informational message using the specified class logger.
     *
     * <p>The provided class is used as the logger source instead of the
     * automatically detected caller.</p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info(MyClass.class, "Loading world generation");
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     */
    public static void info(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).info(message);
    }

    /**
     * Logs an informational message using the caller's logger.
     *
     * <p>The calling class is automatically detected through the shared
     * {@link StackWalker} instance.</p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.info("Loading assets");
     * }</pre>
     *
     * @param message the message to log
     */
    public static void info(String message) {
        getCallerLogger().info(message);
    }

    /**
     * Logs a warning message using the caller's logger.
     *
     * <p>Warnings should be used for non-fatal situations where execution can
     * continue but something unexpected occurred.</p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.warn("Missing optional resource");
     * }</pre>
     *
     * @param message the message to log
     */
    public static void warn(String message) {
        getCallerLogger().warn(message);
    }

    /**
     * Logs a warning message using the specified class logger.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.warn(WorldGen.class, "Feature was skipped");
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     */
    public static void warn(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).warn(message);
    }

    /**
     * Logs an error message using the caller's logger.
     *
     * <p>Errors indicate that an operation failed and may require developer
     * attention.</p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.error("Failed to register block");
     * }</pre>
     *
     * @param message the message to log
     */
    public static void error(String message) {
        getCallerLogger().error(message);
    }

    /**
     * Logs an error message using the specified class logger.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * LogUtil.error(MyClass.class, "Unexpected exception");
     * }</pre>
     *
     * @param clazz the logger owner
     * @param message the message to log
     */
    public static void error(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).error(message);
    }

    /**
     * Resolves the logger associated with the calling class.
     *
     * <p>This method is used internally by the automatic logging methods and
     * should not normally be called directly.</p>
     *
     * @return the caller's logger instance
     */
    private static Logger getCallerLogger() {
        return LoggerFactory.getLogger(STACK_WALKER.getCallerClass());
    }
}