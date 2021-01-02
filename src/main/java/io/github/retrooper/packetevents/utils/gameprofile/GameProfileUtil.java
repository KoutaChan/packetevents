/*
 * MIT License
 *
 * Copyright (c) 2020 retrooper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.retrooper.packetevents.utils.gameprofile;

import io.github.retrooper.packetevents.injector.lateinjector.LateChannelInjector;

import java.util.UUID;

public class GameProfileUtil {
    /**
     * Create a new game profile with the specified arguments.
     *
     * @param uuid     Player UUID.
     * @param username Player username.
     * @return Mojang Game Profile Object.
     */
    public static Object getGameProfile(UUID uuid, String username) {
        if (LateChannelInjector.v1_7_nettyMode) {
            return GameProfileUtil_7.getGameProfile(uuid, username);
        } else {
            return GameProfileUtil_8.getGameProfile(uuid, username);
        }
    }

    /**
     * Create a wrapper over a Mojang Game Profile.
     *
     * @param gameProfile Mojang Game Profile.
     * @return {@link WrappedGameProfile}
     */
    public static WrappedGameProfile getWrappedGameProfile(Object gameProfile) {
        if (LateChannelInjector.v1_7_nettyMode) {
            return GameProfileUtil_7.getWrappedGameProfile(gameProfile);
        } else {
            return GameProfileUtil_8.getWrappedGameProfile(gameProfile);
        }
    }
}
