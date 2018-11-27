/*
 *  Copyright (c) 2011-2015 The original author or authors
 *  ------------------------------------------------------
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *       The Eclipse Public License is available at
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *       The Apache License v2.0 is available at
 *       http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package com.taobao.middleware.cli;

import java.util.List;

/**
 * Interface defining a command-line interface (in other words a command such as 'run', 'ls'...).
 * This interface is polyglot to ease reuse such as in Vert.x Shell.
 * <p/>
 * A command line interface has a name, and defines a set of options and arguments. Options are key-value pair such
 * as {@code -foo=bar} or {@code -flag}. The supported formats depend on the used parser. Arguments are unlike
 * options raw values. Options are defined using
 * {@link Option}, while argument are defined using {@link Argument}.
 * <p/>
 * Command line interfaces also define a summary and a description. These attributes are used in the usage generation
 * . To disable the help generation, set the {@code hidden} attribute to {@code true}.
 * <p/>
 * Command Line Interface object does not contains "value", it's a model. It must be evaluated by a
 * parser that returns a {@link CommandLine} object containing the argument and option values.
 *
 * @author Clement Escoffier <clement@apache.org>
 * @see Argument
 * @see Option
 */
public interface CLI {
    /**
     * Parses the user command line interface and create a new {@link CommandLine} containing extracting values.
     *
     * @param arguments the arguments
     * @return the creates command line
     */
    CommandLine parse(List<String> arguments);

    /**
     * Parses the user command line interface and create a new {@link CommandLine} containing extracting values.
     *
     * @param arguments the arguments
     * @param validate  enable / disable parsing validation
     * @return the creates command line
     */
    CommandLine parse(List<String> arguments, boolean validate);

    /**
     * @return the CLI name.
     */
    String getName();

    /**
     * Sets the name of the CLI.
     *
     * @param name the name
     * @return the current {@link CLI} instance
     */
    CLI setName(String name);

    /**
     * @return the CLI description.
     */
    String getDescription();

    CLI setDescription(String desc);

    /**
     * @return the CLI summary.
     */
    String getSummary();

    /**
     * Sets the summary of the CLI.
     *
     * @param summary the summary
     * @return the current {@link CLI} instance
     */
    CLI setSummary(String summary);

    /**
     * Checks whether or not the current {@link CLI} instance is hidden.
     *
     * @return {@code true} if the current {@link CLI} is hidden, {@link false} otherwise
     */
    boolean isHidden();

    /**
     * Sets whether or not the current instance of {@link CLI} must be hidden. Hidden CLI are not listed when
     * displaying usages / help messages. In other words, hidden commands are for power user.
     *
     * @param hidden enables or disables the hidden aspect of the CI
     * @return the current {@link CLI} instance
     */
    CLI setHidden(boolean hidden);

    /**
     * Gets the list of options.
     *
     * @return the list of options, empty if none.
     */
    List<Option> getOptions();

    /**
     * Adds an option.
     *
     * @param option the option, must not be {@code null}.
     * @return the current {@link CLI} instance
     */
    CLI addOption(Option option);

    /**
     * Adds a set of options. Unlike {@link #setOptions(List)}}, this method does not remove the existing options.
     * The given list is appended to the existing list.
     *
     * @param options the options, must not be {@code null}
     * @return the current {@link CLI} instance
     */
    CLI addOptions(List<Option> options);

    /**
     * Sets the list of arguments.
     *
     * @param options the list of options, must not be {@code null}
     * @return the current {@link CLI} instance
     */
    CLI setOptions(List<Option> options);

    /**
     * Gets the list of defined arguments.
     *
     * @return the list of argument, empty if none.
     */
    List<Argument> getArguments();

    /**
     * Adds an argument.
     *
     * @param arg the argument, must not be {@code null}
     * @return the current {@link CLI} instance
     */
    CLI addArgument(Argument arg);

    /**
     * Adds a set of arguments. Unlike {@link #setArguments(List)}, this method does not remove the existing arguments.
     * The given list is appended to the existing list.
     *
     * @param args the arguments, must not be {@code null}
     * @return the current {@link CLI} instance
     */
    CLI addArguments(List<Argument> args);

    /**
     * Sets the list of arguments.
     *
     * @param args the list of arguments, must not be {@code null}
     * @return the current {@link CLI} instance
     */
    CLI setArguments(List<Argument> args);

    /**
     * Gets an {@link Option} based on its name (short name, long name or argument name).
     *
     * @param name the name, must not be {@code null}
     * @return the {@link Option}, {@code null} if not found
     */
    Option getOption(String name);

    /**
     * Gets an {@link Argument} based on its name (argument name).
     *
     * @param name the name of the argument, must not be {@code null}
     * @return the {@link Argument}, {@code null} if not found.
     */
    Argument getArgument(String name);

    /**
     * Gets an {@link Argument} based on its index.
     *
     * @param index the index, must be positive or zero.
     * @return the {@link Argument}, {@code null} if not found.
     */
    Argument getArgument(int index);

    /**
     * Removes an option identified by its name. This method does nothing if the option cannot be found.
     *
     * @param name the option name
     * @return the current {@link CLI} instance
     */
    CLI removeOption(String name);

    /**
     * Removes an argument identified by its index. This method does nothing if the argument cannot be found.
     *
     * @param index the argument index
     * @return the current {@link CLI} instance
     */
    CLI removeArgument(int index);

    /**
     * Generates the usage / help of the current {@link CLI}.
     *
     * @param builder the string builder in which the help is going to be printed
     * @return the current {@link CLI} instance
     */
    CLI usage(StringBuilder builder);

    /**
     * Generates the usage / help of the current {@link CLI}.
     *
     * @param builder the string builder in which the help is going to be printed
     * @param prefix  an optional prefix
     * @return the current {@link CLI} instance
     */
    CLI usage(StringBuilder builder, String prefix);

    /**
     * Generates the usage / help of the current {@link CLI} with customized usage formatter.
     *
     * @param builder the string builder in which the help is going to be printed
     * @param formatter the formatter object used to format the {@link CLI}
     * @return the current {@link CLI} instance
     */
    CLI usage(StringBuilder builder, UsageMessageFormatter formatter);

    boolean isCaseSensitive();
    void setCaseSensitive(boolean caseSensitive);
}
