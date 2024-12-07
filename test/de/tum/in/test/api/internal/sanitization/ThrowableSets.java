package de.tum.in.test.api.internal.sanitization;

import de.tum.in.test.api.security.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

final class ThrowableSets {

    /**
     * Classes in this set must satisfy that ...
     * <ul>
     * <li>they are known and trusted at compile time of Ares <b>and</b></li>
     * <li>they have no more fields than Throwable itself <b>or</b></li>
     * <li>all their fields are (recursively) final and immutable classes, all of
     * which are known and trusted at compile time <b>or</b></li>
     * <li>access to the fields is restricted and controlled enough that the class
     * and its fields can safely be categorized as immutable</li>
     * </ul>
     * Classes whose instances can be insecurely modified after creation or that
     * allow references to unknown (sub-) classes <b>must not be contained</b> in
     * this set.
     */
    static final Set<Class<? extends Throwable>> SAFE_TYPES;

    private static final Logger LOG = LoggerFactory.getLogger(ThrowableSets.class);

    private static final String JUNIT4_CHECK_CLASS = "junit.framework.AssertionFailedError"; //$NON-NLS-1$
    private static final String JUNIT5_CHECK_CLASS = "org.junit.platform.commons.JUnitException"; //$NON-NLS-1$
    private static final String ASSERTJ_CHECK_CLASS = "org.assertj.core.api.Assertions"; //$NON-NLS-1$
    private static final String OPENTEST4J_CHECK_CLASS = "org.opentest4j.AssertionFailedError"; //$NON-NLS-1$

    static {
        HashSet<Class<? extends Throwable>> join = new HashSet<>(Java.SAFE_TYPES);
        join.addAll(Own.SAFE_TYPES);
        if (classCanBeFound(JUNIT4_CHECK_CLASS))
            join.addAll(JUnit4.SAFE_TYPES);
        if (classCanBeFound(JUNIT5_CHECK_CLASS))
            join.addAll(JUnit5.SAFE_TYPES);
        if (classCanBeFound(ASSERTJ_CHECK_CLASS))
            join.addAll(AssertJ.SAFE_TYPES);
        if (classCanBeFound(OPENTEST4J_CHECK_CLASS))
            join.addAll(OpenTest4J.SAFE_TYPES);
        SAFE_TYPES = Collections.unmodifiableSet(join);
    }

    private ThrowableSets() {
    }

    private static boolean classCanBeFound(String className) {
        try {
            Class.forName(className, false, Thread.currentThread().getContextClassLoader());
            return true;
        } catch (@SuppressWarnings("unused") ClassNotFoundException e) {
            return false;
        } catch (Exception e) {
            LOG.error("Loading class " + className + "failed", e); //$NON-NLS-1$ //$NON-NLS-2$
            return false;
        }
    }

    public static void init() {
        // just for initialization
    }

    static final class Own {

        static final Set<Class<? extends Throwable>> SAFE_TYPES = Set.of(ConfigurationException.class);

        private Own() {
        }
    }

    static final class Java {

        static final Set<Class<? extends Throwable>> SAFE_TYPES = Set.of(java.awt.AWTError.class,
                java.awt.AWTException.class, java.awt.FontFormatException.class, java.awt.HeadlessException.class,
                java.awt.IllegalComponentStateException.class, java.awt.color.CMMException.class,
                java.awt.color.ProfileDataException.class, java.awt.datatransfer.MimeTypeParseException.class,
                java.awt.datatransfer.UnsupportedFlavorException.class, java.awt.dnd.InvalidDnDOperationException.class,
                java.awt.geom.IllegalPathStateException.class, java.awt.geom.NoninvertibleTransformException.class,
                java.awt.image.ImagingOpException.class, java.awt.image.RasterFormatException.class,
                java.awt.print.PrinterAbortException.class, java.awt.print.PrinterException.class,
                java.beans.IntrospectionException.class, java.io.CharConversionException.class,
                java.io.EOFException.class, java.io.FileNotFoundException.class, java.io.IOError.class,
                java.io.IOException.class, java.io.InterruptedIOException.class, java.io.InvalidClassException.class,
                java.io.InvalidObjectException.class, java.io.NotActiveException.class,
                java.io.NotSerializableException.class, java.io.OptionalDataException.class,
                java.io.StreamCorruptedException.class, java.io.SyncFailedException.class,
                java.io.UTFDataFormatException.class, java.io.UncheckedIOException.class,
                java.io.UnsupportedEncodingException.class, AbstractMethodError.class,
                ArithmeticException.class, ArrayIndexOutOfBoundsException.class,
                ArrayStoreException.class, AssertionError.class,
                BootstrapMethodError.class, ClassCastException.class,
                ClassCircularityError.class, ClassFormatError.class,
                CloneNotSupportedException.class, EnumConstantNotPresentException.class,
                Error.class, Exception.class, IllegalAccessError.class,
                IllegalAccessException.class, IllegalArgumentException.class,
                IllegalCallerException.class, IllegalMonitorStateException.class,
                IllegalStateException.class, IllegalThreadStateException.class,
                IncompatibleClassChangeError.class, IndexOutOfBoundsException.class,
                InstantiationError.class, InstantiationException.class,
                InternalError.class, InterruptedException.class,
                LayerInstantiationException.class, LinkageError.class,
                NegativeArraySizeException.class, NoClassDefFoundError.class,
                NoSuchFieldError.class, NoSuchFieldException.class,
                NoSuchMethodError.class, NoSuchMethodException.class,
                NullPointerException.class, NumberFormatException.class,
                OutOfMemoryError.class, ReflectiveOperationException.class,
                RuntimeException.class, SecurityException.class, StackOverflowError.class,
                StringIndexOutOfBoundsException.class, ThreadDeath.class, Throwable.class,
                TypeNotPresentException.class, UnknownError.class,
                UnsatisfiedLinkError.class, UnsupportedClassVersionError.class,
                UnsupportedOperationException.class, VerifyError.class,
                java.lang.annotation.AnnotationFormatError.class,
                java.lang.annotation.AnnotationTypeMismatchException.class,
                java.lang.annotation.IncompleteAnnotationException.class,
                java.lang.instrument.IllegalClassFormatException.class,
                java.lang.instrument.UnmodifiableClassException.class,
                java.lang.instrument.UnmodifiableModuleException.class,
                java.lang.invoke.LambdaConversionException.class, java.lang.invoke.StringConcatException.class,
                java.lang.invoke.WrongMethodTypeException.class, java.lang.module.FindException.class,
                java.lang.module.InvalidModuleDescriptorException.class, java.lang.module.ResolutionException.class,
                java.lang.reflect.GenericSignatureFormatError.class,
                java.lang.reflect.InaccessibleObjectException.class,
                java.lang.reflect.MalformedParameterizedTypeException.class,
                java.lang.reflect.MalformedParametersException.class, java.net.BindException.class,
                java.net.ConnectException.class, java.net.HttpRetryException.class,
                java.net.MalformedURLException.class, java.net.NoRouteToHostException.class,
                java.net.PortUnreachableException.class, java.net.ProtocolException.class,
                java.net.SocketException.class, java.net.SocketTimeoutException.class,
                java.net.URISyntaxException.class, java.net.UnknownHostException.class,
                java.net.UnknownServiceException.class, java.net.http.HttpConnectTimeoutException.class,
                java.net.http.HttpTimeoutException.class, java.nio.BufferOverflowException.class,
                java.nio.BufferUnderflowException.class, java.nio.InvalidMarkException.class,
                java.nio.ReadOnlyBufferException.class, java.nio.channels.AcceptPendingException.class,
                java.nio.channels.AlreadyBoundException.class, java.nio.channels.AlreadyConnectedException.class,
                java.nio.channels.AsynchronousCloseException.class, java.nio.channels.CancelledKeyException.class,
                java.nio.channels.ClosedByInterruptException.class, java.nio.channels.ClosedChannelException.class,
                java.nio.channels.ClosedSelectorException.class, java.nio.channels.ConnectionPendingException.class,
                java.nio.channels.FileLockInterruptionException.class,
                java.nio.channels.IllegalBlockingModeException.class,
                java.nio.channels.IllegalChannelGroupException.class, java.nio.channels.IllegalSelectorException.class,
                java.nio.channels.InterruptedByTimeoutException.class,
                java.nio.channels.NoConnectionPendingException.class,
                java.nio.channels.NonReadableChannelException.class,
                java.nio.channels.NonWritableChannelException.class, java.nio.channels.NotYetBoundException.class,
                java.nio.channels.NotYetConnectedException.class, java.nio.channels.OverlappingFileLockException.class,
                java.nio.channels.ReadPendingException.class, java.nio.channels.ShutdownChannelGroupException.class,
                java.nio.channels.UnresolvedAddressException.class,
                java.nio.channels.UnsupportedAddressTypeException.class, java.nio.channels.WritePendingException.class,
                java.nio.charset.CharacterCodingException.class, java.nio.charset.CoderMalfunctionError.class,
                java.nio.charset.IllegalCharsetNameException.class, java.nio.charset.MalformedInputException.class,
                java.nio.charset.UnmappableCharacterException.class, java.nio.charset.UnsupportedCharsetException.class,
                java.nio.file.AccessDeniedException.class, java.nio.file.AtomicMoveNotSupportedException.class,
                java.nio.file.ClosedDirectoryStreamException.class, java.nio.file.ClosedFileSystemException.class,
                java.nio.file.ClosedWatchServiceException.class, java.nio.file.DirectoryIteratorException.class,
                java.nio.file.DirectoryNotEmptyException.class, java.nio.file.FileAlreadyExistsException.class,
                java.nio.file.FileSystemAlreadyExistsException.class, java.nio.file.FileSystemException.class,
                java.nio.file.FileSystemLoopException.class, java.nio.file.FileSystemNotFoundException.class,
                java.nio.file.InvalidPathException.class, java.nio.file.NoSuchFileException.class,
                java.nio.file.NotDirectoryException.class, java.nio.file.NotLinkException.class,
                java.nio.file.ProviderMismatchException.class, java.nio.file.ProviderNotFoundException.class,
                java.nio.file.ReadOnlyFileSystemException.class,
                java.nio.file.attribute.UserPrincipalNotFoundException.class, java.rmi.AlreadyBoundException.class,
                java.rmi.NotBoundException.class, java.rmi.server.ServerNotActiveException.class,
                java.security.DigestException.class, java.security.GeneralSecurityException.class,
                java.security.InvalidAlgorithmParameterException.class, java.security.InvalidKeyException.class,
                java.security.InvalidParameterException.class, java.security.KeyException.class,
                java.security.KeyManagementException.class, java.security.KeyStoreException.class,
                java.security.NoSuchAlgorithmException.class, java.security.NoSuchProviderException.class,
                java.security.ProviderException.class, java.security.SignatureException.class,
                java.security.UnrecoverableEntryException.class, java.security.UnrecoverableKeyException.class,
                java.security.cert.CRLException.class, java.security.cert.CertPathBuilderException.class,
                java.security.cert.CertStoreException.class, java.security.cert.CertificateEncodingException.class,
                java.security.cert.CertificateException.class, java.security.cert.CertificateExpiredException.class,
                java.security.cert.CertificateNotYetValidException.class,
                java.security.cert.CertificateParsingException.class, java.security.spec.InvalidKeySpecException.class,
                java.security.spec.InvalidParameterSpecException.class, java.text.ParseException.class,
                java.time.DateTimeException.class, java.time.format.DateTimeParseException.class,
                java.time.temporal.UnsupportedTemporalTypeException.class, java.time.zone.ZoneRulesException.class,
                ConcurrentModificationException.class, DuplicateFormatFlagsException.class,
                EmptyStackException.class, FormatFlagsConversionMismatchException.class,
                FormatterClosedException.class, IllegalFormatCodePointException.class,
                IllegalFormatConversionException.class, IllegalFormatException.class,
                IllegalFormatFlagsException.class, IllegalFormatPrecisionException.class,
                IllegalFormatWidthException.class, IllformedLocaleException.class,
                InputMismatchException.class, InvalidPropertiesFormatException.class,
                MissingFormatArgumentException.class, MissingFormatWidthException.class,
                MissingResourceException.class, NoSuchElementException.class,
                ServiceConfigurationError.class, TooManyListenersException.class,
                UnknownFormatConversionException.class, UnknownFormatFlagsException.class,
                java.util.concurrent.BrokenBarrierException.class, java.util.concurrent.CancellationException.class,
                java.util.concurrent.CompletionException.class, java.util.concurrent.ExecutionException.class,
                java.util.concurrent.RejectedExecutionException.class, java.util.concurrent.TimeoutException.class,
                java.util.jar.JarException.class, java.util.prefs.BackingStoreException.class,
                java.util.prefs.InvalidPreferencesFormatException.class, java.util.regex.PatternSyntaxException.class,
                java.util.zip.DataFormatException.class, java.util.zip.ZipError.class,
                java.util.zip.ZipException.class);

        private Java() {
        }
    }

    static final class JUnit4 {

        static final Set<Class<? extends Throwable>> SAFE_TYPES = Set.of(junit.framework.AssertionFailedError.class,
                junit.framework.ComparisonFailure.class, org.junit.ComparisonFailure.class, org.junit.Test.None.class,
                org.junit.TestCouldNotBeSkippedException.class,
                org.junit.experimental.max.CouldNotReadCoreException.class,
                org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException.class,
                org.junit.experimental.theories.internal.ParameterizedAssertionError.class,
                org.junit.internal.ArrayComparisonFailure.class,
                org.junit.runner.FilterFactory.FilterNotCreatedException.class,
                org.junit.runner.manipulation.InvalidOrderingException.class,
                org.junit.runner.manipulation.NoTestsRemainException.class,
                org.junit.runner.notification.StoppedByUserException.class,
                org.junit.runners.model.TestTimedOutException.class);

        private JUnit4() {
        }
    }

    static final class JUnit5 {

        static final Set<Class<? extends Throwable>> SAFE_TYPES = Set.of(
                org.junit.jupiter.api.extension.ExtensionConfigurationException.class,
                org.junit.jupiter.api.extension.ExtensionContextException.class,
                org.junit.jupiter.api.extension.ParameterResolutionException.class,
                org.junit.jupiter.api.extension.TestInstantiationException.class,
                org.junit.platform.commons.JUnitException.class,
                org.junit.platform.commons.PreconditionViolationException.class);

        private JUnit5() {
        }
    }

    static final class AssertJ {

        static final Set<Class<? extends Throwable>> SAFE_TYPES = Set.of(
                org.assertj.core.api.exception.PathsException.class,
                org.assertj.core.util.introspection.IntrospectionError.class);

        private AssertJ() {
        }
    }

    static final class OpenTest4J {

        static final Set<Class<? extends Throwable>> SAFE_TYPES = Set.of(
                org.opentest4j.IncompleteExecutionException.class, org.opentest4j.TestAbortedException.class,
                org.opentest4j.TestSkippedException.class);

        private OpenTest4J() {
        }
    }
}
