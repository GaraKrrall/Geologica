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

import net.minecraft.resources.Identifier;

/**
 * Utility class containing helper methods related to Minecraft resource
 * locations and identifiers.
 *
 * <p>The primary purpose of this class is to abstract away API differences
 * between Minecraft versions so that resource locations can be created from
 * a single, consistent entry point.</p>
 *
 * <p>Most projects only need a single helper method, but this class exists
 * as a centralized location for future identifier and resource path related
 * utilities.</p>
 */
public class LocationUtil {
    /**
     * Constructs a new utility instance.
     *
     * <p>This constructor is intentionally inaccessible because this class only
     * contains static utility methods.</p>
     *
     * @throws UnsupportedOperationException always
     */
    private LocationUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Creates a Minecraft {@link Identifier} while automatically adapting to
     * version-specific API changes.
     *
     * <p>Minecraft 1.21 replaced the public {@code Identifier(namespace, path)}
     * constructor with static factory methods such as
     * {@link Identifier#fromNamespaceAndPath(String, String)}.</p>
     *
     * <p>This utility method exists to hide those differences behind a single
     * implementation, allowing the same codebase to compile against multiple
     * Minecraft versions without repeatedly checking which constructor or
     * factory method should be used.</p>
     *
     * <p>Instead of writing version-specific code:</p>
     *
     * <pre>{@code
     * // 1.20.6 and below
     * new Identifier("geologica", "oil_shale");
     *
     * // 1.21.6 and above
     * Identifier.fromNamespaceAndPath("geologica", "oil_shale");
     * }</pre>
     *
     * <p>You can simply use:</p>
     *
     * <pre>{@code
     * LocationUtil.modIdentifier("geologica", "oil_shale");
     * }</pre>
     *
     * <p>This method should be preferred whenever creating resource locations
     * within Geologica.</p>
     *
     * @param namespace the namespace of the identifier, usually a mod id
     * @param path the resource path within the namespace
     * @return a version-compatible identifier
     */
    public static Identifier modIdentifier(String namespace, String path) {
        //? if <1.21 {
        /*return new Identifier(namespace, path);
         *///?} else
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}
