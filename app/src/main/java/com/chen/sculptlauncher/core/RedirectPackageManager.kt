package com.chen.sculptlauncher.core

import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.ChangedPackages
import android.content.pm.FeatureInfo
import android.content.pm.InstrumentationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.content.pm.PermissionGroupInfo
import android.content.pm.PermissionInfo
import android.content.pm.ProviderInfo
import android.content.pm.ResolveInfo
import android.content.pm.ServiceInfo
import android.content.pm.SharedLibraryInfo
import android.content.pm.VersionedPackage
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.UserHandle


class RedirectPackageManager(
    protected var wrapped: PackageManager,
    protected var nativeLibraryDir: String
) :
    PackageManager() {
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Throws(
        NameNotFoundException::class
    )
    override fun getActivityInfo(className: ComponentName, flags: Int): ActivityInfo {
        val retval = wrapped.getActivityInfo(className, flags)
        retval.applicationInfo.nativeLibraryDir = nativeLibraryDir
        return retval
    }

    override fun getPackageInstaller(): PackageInstaller {
        return wrapped.packageInstaller
    }

    override fun canRequestPackageInstalls(): Boolean {
        return wrapped.canRequestPackageInstalls()
    }

    override fun getUserBadgedLabel(label: CharSequence, user: UserHandle): CharSequence {
        return wrapped.getUserBadgedLabel(label, user)
    }

    override fun getUserBadgedDrawableForDensity(
        drawable: Drawable,
        user: UserHandle,
        badgeLocation: Rect?,
        badgeDensity: Int
    ): Drawable {
        return wrapped.getUserBadgedDrawableForDensity(drawable, user, badgeLocation, badgeDensity)
    }

    override fun getUserBadgedIcon(icon: Drawable, user: UserHandle): Drawable {
        return wrapped.getUserBadgedIcon(icon, user)
    }

    @Throws(NameNotFoundException::class)
    override fun getApplicationBanner(packageName: String): Drawable? {
        return wrapped.getApplicationBanner(packageName)
    }

    override fun getApplicationBanner(info: ApplicationInfo): Drawable? {
        return wrapped.getApplicationBanner(info)
    }

    @Throws(NameNotFoundException::class)
    override fun getActivityBanner(intent: Intent): Drawable? {
        return wrapped.getActivityBanner(intent)
    }

    @Throws(NameNotFoundException::class)
    override fun getActivityBanner(activityName: ComponentName): Drawable? {
        return wrapped.getActivityBanner(activityName)
    }

    override fun getLeanbackLaunchIntentForPackage(packageName: String): Intent? {
        return wrapped.getLeanbackLaunchIntentForPackage(packageName)
    }

    @Throws(NameNotFoundException::class)
    override fun getPackageInfo(packageName: String, flags: Int): PackageInfo {
        return wrapped.getPackageInfo(packageName, flags)
    }

    override fun getPackageInfo(versionedPackage: VersionedPackage, flags: Int): PackageInfo {
        return  wrapped.getPackageInfo(versionedPackage, flags)
    }

    override fun currentToCanonicalPackageNames(names: Array<String>): Array<String> {
        return wrapped.currentToCanonicalPackageNames(names)
    }

    override fun canonicalToCurrentPackageNames(names: Array<String>): Array<String> {
        return wrapped.canonicalToCurrentPackageNames(names)
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return wrapped.getLaunchIntentForPackage(packageName)
    }

    @Throws(NameNotFoundException::class)
    override fun getPackageGids(packageName: String): IntArray {
        return wrapped.getPackageGids(packageName)
    }

    override fun getPackageGids(packageName: String, flags: Int): IntArray {
        return wrapped.getPackageGids(packageName, flags)
    }

    override fun getPackageUid(packageName: String, flags: Int): Int {
        return wrapped.getPackageUid(packageName, flags)
    }

    @Throws(NameNotFoundException::class)
    override fun getPermissionInfo(name: String, flags: Int): PermissionInfo {
        return wrapped.getPermissionInfo(name, flags)
    }

    @Throws(NameNotFoundException::class)
    override fun queryPermissionsByGroup(group: String?, flags: Int): List<PermissionInfo> {
        return wrapped.queryPermissionsByGroup(group, flags)
    }

    @Throws(NameNotFoundException::class)
    override fun getPermissionGroupInfo(
        name: String,
        flags: Int
    ): PermissionGroupInfo {
        return wrapped.getPermissionGroupInfo(name, flags)
    }

    override fun getAllPermissionGroups(flags: Int): List<PermissionGroupInfo> {
        return wrapped.getAllPermissionGroups(flags)
    }

    @Throws(NameNotFoundException::class)
    override fun getApplicationInfo(packageName: String, flags: Int): ApplicationInfo {
        return wrapped.getApplicationInfo(packageName, flags)
    }

    @Throws(NameNotFoundException::class)
    override fun getReceiverInfo(className: ComponentName, flags: Int): ActivityInfo {
        return wrapped.getReceiverInfo(className, flags)
    }

    @Throws(NameNotFoundException::class)
    override fun getServiceInfo(className: ComponentName, flags: Int): ServiceInfo {
        return wrapped.getServiceInfo(className, flags)
    }

    @Throws(NameNotFoundException::class)
    override fun getProviderInfo(className: ComponentName, flags: Int): ProviderInfo {
        return wrapped.getProviderInfo(className, flags)
    }

    override fun getInstalledPackages(flags: Int): List<PackageInfo> {
        return wrapped.getInstalledPackages(flags)
    }

    override fun checkPermission(permName: String, pkgName: String): Int {
        return wrapped.checkPermission(permName, pkgName)
    }

    override fun isPermissionRevokedByPolicy(permName: String, packageName: String): Boolean {
        return wrapped.isPermissionRevokedByPolicy(permName, packageName)
    }

    override fun addPermission(info: PermissionInfo): Boolean {
        return wrapped.addPermission(info)
    }

    override fun addPermissionAsync(info: PermissionInfo): Boolean {
        return wrapped.addPermissionAsync(info)
    }

    override fun removePermission(name: String) {
        wrapped.removePermission(name)
    }

    override fun checkSignatures(pkg1: String, pkg2: String): Int {
        return wrapped.checkSignatures(pkg1, pkg2)
    }

    override fun checkSignatures(uid1: Int, uid2: Int): Int {
        return wrapped.checkSignatures(uid1, uid2)
    }

    override fun getPackagesForUid(uid: Int): Array<String>? {
        return wrapped.getPackagesForUid(uid)
    }

    override fun getNameForUid(uid: Int): String? {
        return wrapped.getNameForUid(uid)
    }

    override fun getInstalledApplications(flags: Int): List<ApplicationInfo> {
        return wrapped.getInstalledApplications(flags)
    }

    override fun isInstantApp(): Boolean {
        return wrapped.isInstantApp
    }

    override fun isInstantApp(packageName: String): Boolean {
        return wrapped.isInstantApp(packageName)
    }

    override fun getInstantAppCookieMaxBytes(): Int {
        return wrapped.instantAppCookieMaxBytes
    }

    override fun getInstantAppCookie(): ByteArray {
        return wrapped.instantAppCookie
    }

    override fun clearInstantAppCookie() {
        return wrapped.clearInstantAppCookie()
    }

    override fun updateInstantAppCookie(cookie: ByteArray?) {
        return wrapped.updateInstantAppCookie(cookie)
    }

    override fun resolveActivity(intent: Intent, flags: Int): ResolveInfo? {
        return wrapped.resolveActivity(intent, flags)
    }

    override fun queryIntentActivities(intent: Intent, flags: Int): List<ResolveInfo> {
        return wrapped.queryIntentActivities(intent, flags)
    }

    override fun queryIntentActivityOptions(
        caller: ComponentName?,
        specifics: Array<Intent>?, intent: Intent, flags: Int
    ): List<ResolveInfo> {
        return wrapped.queryIntentActivityOptions(caller, specifics, intent, flags)
    }

    override fun queryBroadcastReceivers(intent: Intent, flags: Int): List<ResolveInfo> {
        return wrapped.queryBroadcastReceivers(intent, flags)
    }

    override fun resolveService(intent: Intent, flags: Int): ResolveInfo? {
        return wrapped.resolveService(intent, flags)
    }

    override fun queryIntentServices(intent: Intent, flags: Int): List<ResolveInfo> {
        return wrapped.queryIntentServices(intent, flags)
    }

    override fun resolveContentProvider(name: String, flags: Int): ProviderInfo? {
        return wrapped.resolveContentProvider(name, flags)
    }

    override fun queryContentProviders(
        processName: String?,
        uid: Int,
        flags: Int
    ): List<ProviderInfo> {
        return wrapped.queryContentProviders(processName, uid, flags)
    }

    @Throws(NameNotFoundException::class)
    override fun getInstrumentationInfo(className: ComponentName, flags: Int): InstrumentationInfo {
        return wrapped.getInstrumentationInfo(className, flags)
    }

    override fun queryInstrumentation(
        targetPackage: String, flags: Int
    ): List<InstrumentationInfo> {
        return wrapped.queryInstrumentation(targetPackage, flags)
    }

    override fun getDrawable(
        packageName: String,
        resid: Int,
        appInfo: ApplicationInfo?
    ): Drawable? {
        return wrapped.getDrawable(packageName, resid, appInfo)
    }

    @Throws(NameNotFoundException::class)
    override fun getActivityIcon(activityName: ComponentName): Drawable {
        return wrapped.getActivityIcon(activityName)
    }

    @Throws(NameNotFoundException::class)
    override fun getActivityIcon(intent: Intent): Drawable {
        return wrapped.getActivityIcon(intent)
    }

    override fun getDefaultActivityIcon(): Drawable {
        return wrapped.defaultActivityIcon
    }

    override fun getApplicationIcon(info: ApplicationInfo): Drawable {
        return wrapped.getApplicationIcon(info)
    }

    @Throws(NameNotFoundException::class)
    override fun getApplicationIcon(packageName: String): Drawable {
        return wrapped.getApplicationIcon(packageName)
    }

    @Throws(NameNotFoundException::class)
    override fun getActivityLogo(activityName: ComponentName): Drawable? {
        return wrapped.getActivityLogo(activityName)
    }

    @Throws(NameNotFoundException::class)
    override fun getActivityLogo(intent: Intent): Drawable? {
        return wrapped.getActivityLogo(intent)
    }

    override fun getApplicationLogo(info: ApplicationInfo): Drawable? {
        return wrapped.getApplicationLogo(info)
    }

    @Throws(NameNotFoundException::class)
    override fun getApplicationLogo(packageName: String): Drawable? {
        return wrapped.getApplicationLogo(packageName)
    }

    override fun getText(
        packageName: String,
        resid: Int,
        appInfo: ApplicationInfo?
    ): CharSequence? {
        return wrapped.getText(packageName, resid, appInfo)
    }

    override fun getXml(
        packageName: String, resid: Int,
        appInfo: ApplicationInfo?
    ): XmlResourceParser? {
        return wrapped.getXml(packageName, resid, appInfo)
    }

    override fun getApplicationLabel(info: ApplicationInfo): CharSequence {
        return wrapped.getApplicationLabel(info)
    }

    @Throws(NameNotFoundException::class)
    override fun getResourcesForActivity(activityName: ComponentName): Resources {
        return wrapped.getResourcesForActivity(activityName)
    }

    @Throws(NameNotFoundException::class)
    override fun getResourcesForApplication(app: ApplicationInfo): Resources {
        return wrapped.getResourcesForApplication(app)
    }

    @Throws(NameNotFoundException::class)
    override fun getResourcesForApplication(appPackageName: String): Resources {
        return wrapped.getResourcesForApplication(appPackageName)
    }

    override fun getPackageArchiveInfo(archiveFilePath: String, flags: Int): PackageInfo? {
        return wrapped.getPackageArchiveInfo(archiveFilePath, flags)
    }

    override fun getInstallerPackageName(packageName: String): String? {
        return wrapped.getInstallerPackageName(packageName)
    }

    @Suppress("deprecation")
    override fun addPackageToPreferred(packageName: String) {
        wrapped.addPackageToPreferred(packageName)
    }

    @Suppress("deprecation")
    override fun removePackageFromPreferred(packageName: String) {
        wrapped.removePackageFromPreferred(packageName)
    }

    override fun getPreferredPackages(flags: Int): List<PackageInfo> {
        return wrapped.getPreferredPackages(flags)
    }

    override fun setComponentEnabledSetting(
        componentName: ComponentName,
        newState: Int, flags: Int
    ) {
        wrapped.setComponentEnabledSetting(componentName, newState, flags)
    }

    override fun getComponentEnabledSetting(componentName: ComponentName): Int {
        return wrapped.getComponentEnabledSetting(componentName)
    }

    override fun setApplicationEnabledSetting(packageName: String, newState: Int, flags: Int) {
        wrapped.setApplicationEnabledSetting(packageName, newState, flags)
    }

    override fun getApplicationEnabledSetting(packageName: String): Int {
        return wrapped.getApplicationEnabledSetting(packageName)
    }

    @Suppress("deprecation")
    override fun addPreferredActivity(
        filter: IntentFilter,
        match: Int, set: Array<ComponentName>?, activity: ComponentName
    ) {
        wrapped.addPreferredActivity(filter, match, set, activity)
    }

    override fun clearPackagePreferredActivities(packageName: String) {
        wrapped.clearPackagePreferredActivities(packageName)
    }

    override fun getPreferredActivities(
        outFilters: List<IntentFilter>,
        outActivities: List<ComponentName>, packageName: String?
    ): Int {
        return wrapped.getPreferredActivities(outFilters, outActivities, packageName)
    }

    override fun getSystemSharedLibraryNames(): Array<String>? {
        return wrapped.systemSharedLibraryNames
    }

    override fun getSharedLibraries(flags: Int): MutableList<SharedLibraryInfo> {
        return wrapped.getSharedLibraries(flags)
    }

    override fun getChangedPackages(sequenceNumber: Int): ChangedPackages? {
        return wrapped.getChangedPackages(sequenceNumber)
    }

    override fun getSystemAvailableFeatures(): Array<FeatureInfo> {
        return wrapped.systemAvailableFeatures
    }

    override fun hasSystemFeature(name: String): Boolean {
        return wrapped.hasSystemFeature(name)
    }

    override fun hasSystemFeature(featureName: String, version: Int): Boolean {
        return wrapped.hasSystemFeature(featureName, version)
    }

    override fun isSafeMode(): Boolean {
        return wrapped.isSafeMode
    }

    override fun setApplicationCategoryHint(packageName: String, categoryHint: Int) {
        return wrapped.setApplicationCategoryHint(packageName, categoryHint)
    }

    override fun getPackagesHoldingPermissions(
        permissions: Array<String>,
        flags: Int
    ): List<PackageInfo> {
        return wrapped.getPackagesHoldingPermissions(permissions, flags)
    }

    override fun queryIntentContentProviders(intent: Intent, flags: Int): List<ResolveInfo> {
        return wrapped.queryIntentContentProviders(intent, flags)
    }

    override fun verifyPendingInstall(id: Int, verificationCode: Int) {
        wrapped.verifyPendingInstall(id, verificationCode)
    }

    override fun extendVerificationTimeout(
        id: Int, verificationCodeAtTimeout: Int,
        millisecondsToDelay: Long
    ) {
        wrapped.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay)
    }

    override fun setInstallerPackageName(targetPackage: String, installerPackageName: String?) {
        wrapped.setInstallerPackageName(targetPackage, installerPackageName)
    }
}
