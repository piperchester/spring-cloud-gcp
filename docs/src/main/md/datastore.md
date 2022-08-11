## Spring Data Cloud Datastore

<div class="note">

This integration is fully compatible with [Firestore in Datastore
Mode](https://cloud.google.com/datastore/docs/), but not with Firestore
in Native Mode.

</div>

[Spring Data](https://projects.spring.io/spring-data/) is an abstraction
for storing and retrieving POJOs in numerous storage technologies.
Spring Cloud GCP adds Spring Data support for [Google Cloud
Firestore](https://cloud.google.com/firestore/) in Datastore mode.

Maven coordinates for this module only, using [Spring Cloud GCP
BOM](getting-started.xml#bill-of-materials):

``` xml
<dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>spring-cloud-gcp-data-datastore</artifactId>
</dependency>
```

Gradle coordinates:

    dependencies {
        implementation("com.google.cloud:spring-cloud-gcp-data-datastore")
    }

We provide a [Spring Boot Starter for Spring Data
Datastore](https://github.com/GoogleCloudPlatform/spring-cloud-gcp/tree/main/spring-cloud-gcp-starters/spring-cloud-gcp-starter-data-datastore),
with which you can use our recommended auto-configuration setup. To use
the starter, see the coordinates below.

Maven:

``` xml
<dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>spring-cloud-gcp-starter-data-datastore</artifactId>
</dependency>
```

Gradle:

    dependencies {
        implementation("com.google.cloud:spring-cloud-gcp-starter-data-datastore")
    }

This setup takes care of bringing in the latest compatible version of
Cloud Java Cloud Datastore libraries as well.

### Configuration

To setup Spring Data Cloud Datastore, you have to configure the
following:

  - Setup the connection details to Google Cloud Datastore.

#### Cloud Datastore settings

You can use the [Spring Boot Starter for Spring Data
Datastore](https://github.com/GoogleCloudPlatform/spring-cloud-gcp/tree/main/spring-cloud-gcp-starters/spring-cloud-gcp-starter-data-datastore)
to autoconfigure Google Cloud Datastore in your Spring application. It
contains all the necessary setup that makes it easy to authenticate with
your Google Cloud project. The following configuration options are
available:

|                                                      |                                                                                                                                                                                                                                                                                                                                     |          |                                                                                                                                                                                                                |
| ---------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Name                                                 | Description                                                                                                                                                                                                                                                                                                                         | Required | Default value                                                                                                                                                                                                  |
| `spring.cloud.gcp.datastore.enabled`                 | Enables the Cloud Datastore client                                                                                                                                                                                                                                                                                                  | No       | `true`                                                                                                                                                                                                         |
| `spring.cloud.gcp.datastore.project-id`              | GCP project ID where the Google Cloud Datastore API is hosted, if different from the one in the [Spring Cloud GCP Core Module](#spring-cloud-gcp-core)                                                                                                                                                                              | No       |                                                                                                                                                                                                                |
| `spring.cloud.gcp.datastore.credentials.location`    | OAuth2 credentials for authenticating with the Google Cloud Datastore API, if different from the ones in the [Spring Cloud GCP Core Module](#spring-cloud-gcp-core)                                                                                                                                                                 | No       |                                                                                                                                                                                                                |
| `spring.cloud.gcp.datastore.credentials.encoded-key` | Base64-encoded OAuth2 credentials for authenticating with the Google Cloud Datastore API, if different from the ones in the [Spring Cloud GCP Core Module](#spring-cloud-gcp-core)                                                                                                                                                  | No       |                                                                                                                                                                                                                |
| `spring.cloud.gcp.datastore.credentials.scopes`      | [OAuth2 scope](https://developers.google.com/identity/protocols/googlescopes) for Spring Cloud GCP Cloud Datastore credentials                                                                                                                                                                                                      | No       | <https://www.googleapis.com/auth/datastore>                                                                                                                                                                    |
| `spring.cloud.gcp.datastore.namespace`               | The Cloud Datastore namespace to use                                                                                                                                                                                                                                                                                                | No       | the Default namespace of Cloud Datastore in your GCP project                                                                                                                                                   |
| `spring.cloud.gcp.datastore.host`                    | The `hostname:port` of the datastore service or emulator to connect to. Can be used to connect to a manually started [Datastore Emulator](https://cloud.google.com/datastore/docs/tools/datastore-emulator). If the autoconfigured emulator is enabled, this property will be ignored and `localhost:<emulator_port>` will be used. | No       |                                                                                                                                                                                                                |
| `spring.cloud.gcp.datastore.emulator.enabled`        | To enable the auto configuration to start a local instance of the Datastore Emulator.                                                                                                                                                                                                                                               | No       | `false`                                                                                                                                                                                                        |
| `spring.cloud.gcp.datastore.emulator.port`           | The local port to use for the Datastore Emulator                                                                                                                                                                                                                                                                                    | No       | `8081`                                                                                                                                                                                                         |
| `spring.cloud.gcp.datastore.emulator.consistency`    | The [consistency](https://cloud.google.com/sdk/gcloud/reference/beta/emulators/datastore/start?#--consistency) to use for the Datastore Emulator instance                                                                                                                                                                           | No       | `0.9`                                                                                                                                                                                                          |
| `spring.cloud.gcp.datastore.emulator.store-on-disk`  | Configures whether or not the emulator should persist any data to disk.                                                                                                                                                                                                                                                             | No       | `true`                                                                                                                                                                                                         |
| `spring.cloud.gcp.datastore.emulator.data-dir`       | The directory to be used to store/retrieve data/config for an emulator run.                                                                                                                                                                                                                                                         | No       | The default value is `<USER_CONFIG_DIR>/emulators/datastore`. See the [gcloud documentation](https://cloud.google.com/sdk/gcloud/reference/beta/emulators/datastore/start) for finding your `USER_CONFIG_DIR`. |

#### Repository settings

Spring Data Repositories can be configured via the
`@EnableDatastoreRepositories` annotation on your main `@Configuration`
class. With our Spring Boot Starter for Spring Data Cloud Datastore,
`@EnableDatastoreRepositories` is automatically added. It is not
required to add it to any other class, unless there is a need to
override finer grain configuration parameters provided by
[`@EnableDatastoreRepositories`](https://github.com/GoogleCloudPlatform/spring-cloud-gcp/blob/main/spring-cloud-gcp-data-datastore/src/main/java/com/google/cloud/spring/data/datastore/repository/config/EnableDatastoreRepositories.java).

#### Autoconfiguration

Our Spring Boot autoconfiguration creates the following beans available
in the Spring application context:

  - an instance of `DatastoreTemplate`

  - an instance of all user defined repositories extending
    `CrudRepository`, `PagingAndSortingRepository`, and
    `DatastoreRepository` (an extension of `PagingAndSortingRepository`
    with additional Cloud Datastore features) when repositories are
    enabled

  - an instance of `Datastore` from the Google Cloud Java Client for
    Datastore, for convenience and lower level API access

#### Datastore Emulator Autoconfiguration

This Spring Boot autoconfiguration can also configure and start a local
Datastore Emulator server if enabled by property.

It is useful for integration testing, but not for production.

When enabled, the `spring.cloud.gcp.datastore.host` property will be
ignored and the Datastore autoconfiguration itself will be forced to
connect to the autoconfigured local emulator instance.

It will create an instance of `LocalDatastoreHelper` as a bean that
stores the `DatastoreOptions` to get the `Datastore` client connection
to the emulator for convenience and lower level API for local access.
The emulator will be properly stopped after the Spring application
context shutdown.

### Object Mapping

Spring Data Cloud Datastore allows you to map domain POJOs to Cloud
Datastore kinds and entities via annotations:

``` java
@Entity(name = "traders")
public class Trader {

    @Id
    @Field(name = "trader_id")
    String traderId;

    String firstName;

    String lastName;

    @Transient
    Double temporaryNumber;
}
```

Spring Data Cloud Datastore will ignore any property annotated with
`@Transient`. These properties will not be written to or read from Cloud
Datastore.

#### Constructors

Simple constructors are supported on POJOs. The constructor arguments
can be a subset of the persistent properties. Every constructor argument
needs to have the same name and type as a persistent property on the
entity and the constructor should set the property from the given
argument. Arguments that are not directly set to properties are not
supported.

``` java
@Entity(name = "traders")
public class Trader {

    @Id
    @Field(name = "trader_id")
    String traderId;

    String firstName;

    String lastName;

    @Transient
    Double temporaryNumber;

    public Trader(String traderId, String firstName) {
        this.traderId = traderId;
        this.firstName = firstName;
    }
}
```

#### Kind

The `@Entity` annotation can provide the name of the Cloud Datastore
kind that stores instances of the annotated class, one per row.

#### Keys

`@Id` identifies the property corresponding to the ID value.

You must annotate one of your POJO’s fields as the ID value, because
every entity in Cloud Datastore requires a single ID value:

``` java
@Entity(name = "trades")
public class Trade {
    @Id
    @Field(name = "trade_id")
    String tradeId;

    @Field(name = "trader_id")
    String traderId;

    String action;

    Double price;

    Double shares;

    String symbol;
}
```

Datastore can automatically allocate integer ID values. If a POJO
instance with a `Long` ID property is written to Cloud Datastore with
`null` as the ID value, then Spring Data Cloud Datastore will obtain a
newly allocated ID value from Cloud Datastore and set that in the POJO
for saving. Because primitive `long` ID properties cannot be `null` and
default to `0`, keys will not be allocated.

#### Fields

All accessible properties on POJOs are automatically recognized as a
Cloud Datastore field. Field naming is generated by the
`PropertyNameFieldNamingStrategy` by default defined on the
`DatastoreMappingContext` bean. The `@Field` annotation optionally
provides a different field name than that of the property.

#### Supported Types

Spring Data Cloud Datastore supports the following types for regular
fields and elements of collections:

| Type                                | Stored as                                 |
| ----------------------------------- | ----------------------------------------- |
| `com.google.cloud.Timestamp`        | com.google.cloud.datastore.TimestampValue |
| `com.google.cloud.datastore.Blob`   | com.google.cloud.datastore.BlobValue      |
| `com.google.cloud.datastore.LatLng` | com.google.cloud.datastore.LatLngValue    |
| `java.lang.Boolean`, `boolean`      | com.google.cloud.datastore.BooleanValue   |
| `java.lang.Double`, `double`        | com.google.cloud.datastore.DoubleValue    |
| `java.lang.Long`, `long`            | com.google.cloud.datastore.LongValue      |
| `java.lang.Integer`, `int`          | com.google.cloud.datastore.LongValue      |
| `java.lang.String`                  | com.google.cloud.datastore.StringValue    |
| `com.google.cloud.datastore.Entity` | com.google.cloud.datastore.EntityValue    |
| `com.google.cloud.datastore.Key`    | com.google.cloud.datastore.KeyValue       |
| `byte[]`                            | com.google.cloud.datastore.BlobValue      |
| Java `enum` values                  | com.google.cloud.datastore.StringValue    |

In addition, all types that can be converted to the ones listed in the
table by
`org.springframework.core.convert.support.DefaultConversionService` are
supported.

#### Custom types

Custom converters can be used extending the type support for user
defined types.

1.  Converters need to implement the
    `org.springframework.core.convert.converter.Converter` interface in
    both directions.

2.  The user defined type needs to be mapped to one of the basic types
    supported by Cloud Datastore.

3.  An instance of both Converters (read and write) needs to be passed
    to the `DatastoreCustomConversions` constructor, which then has to
    be made available as a `@Bean` for `DatastoreCustomConversions`.

For example:

We would like to have a field of type `Album` on our `Singer` POJO and
want it to be stored as a string property:

``` java
@Entity
public class Singer {

    @Id
    String singerId;

    String name;

    Album album;
}
```

Where Album is a simple class:

``` java
public class Album {
    String albumName;

    LocalDate date;
}
```

We have to define the two converters:

``` java
 //Converter to write custom Album type
    static final Converter<Album, String> ALBUM_STRING_CONVERTER =
            new Converter<Album, String>() {
                @Override
                public String convert(Album album) {
                    return album.getAlbumName() + " " + album.getDate().format(DateTimeFormatter.ISO_DATE);
                }
            };

    //Converters to read custom Album type
    static final Converter<String, Album> STRING_ALBUM_CONVERTER =
            new Converter<String, Album>() {
                @Override
                public Album convert(String s) {
                    String[] parts = s.split(" ");
                    return new Album(parts[0], LocalDate.parse(parts[parts.length - 1], DateTimeFormatter.ISO_DATE));
                }
            };
```

That will be configured in our `@Configuration` file:

``` java
@Configuration
public class ConverterConfiguration {
    @Bean
    public DatastoreCustomConversions datastoreCustomConversions() {
        return new DatastoreCustomConversions(
                Arrays.asList(
                        ALBUM_STRING_CONVERTER,
                        STRING_ALBUM_CONVERTER));
    }
}
```

#### Collections and arrays

Arrays and collections (types that implement `java.util.Collection`) of
supported types are supported. They are stored as
`com.google.cloud.datastore.ListValue`. Elements are converted to Cloud
Datastore supported types individually. `byte[]` is an exception, it is
converted to `com.google.cloud.datastore.Blob`.

#### Custom Converter for collections

Users can provide converters from `List<?>` to the custom collection
type. Only read converter is necessary, the Collection API is used on
the write side to convert a collection to the internal list type.

Collection converters need to implement the
`org.springframework.core.convert.converter.Converter` interface.

Example:

Let’s improve the Singer class from the previous example. Instead of a
field of type `Album`, we would like to have a field of type
`Set<Album>`:

``` java
@Entity
public class Singer {

    @Id
    String singerId;

    String name;

    Set<Album> albums;
}
```

We have to define a read converter only:

``` java
static final Converter<List<?>, Set<?>> LIST_SET_CONVERTER =
            new Converter<List<?>, Set<?>>() {
                @Override
                public Set<?> convert(List<?> source) {
                    return Collections.unmodifiableSet(new HashSet<>(source));
                }
            };
```

And add it to the list of custom converters:

``` java
@Configuration
public class ConverterConfiguration {
    @Bean
    public DatastoreCustomConversions datastoreCustomConversions() {
        return new DatastoreCustomConversions(
                Arrays.asList(
                        LIST_SET_CONVERTER,
                        ALBUM_STRING_CONVERTER,
                        STRING_ALBUM_CONVERTER));
    }
}
```

#### Inheritance Hierarchies

Java entity types related by inheritance can be stored in the same Kind.
When reading and querying entities using `DatastoreRepository` or
`DatastoreTemplate` with a superclass as the type parameter, you can
receive instances of subclasses if you annotate the superclass and its
subclasses with `DiscriminatorField` and `DiscriminatorValue`:

``` java
@Entity(name = "pets")
@DiscriminatorField(field = "pet_type")
abstract class Pet {
    @Id
    Long id;

    abstract String speak();
}

@DiscriminatorValue("cat")
class Cat extends Pet {
    @Override
    String speak() {
        return "meow";
    }
}

@DiscriminatorValue("dog")
class Dog extends Pet {
    @Override
    String speak() {
        return "woof";
    }
}

@DiscriminatorValue("pug")
class Pug extends Dog {
    @Override
    String speak() {
        return "woof woof";
    }
}
```

Instances of all 3 types are stored in the `pets` Kind. Because a single
Kind is used, all classes in the hierarchy must share the same ID
property and no two instances of any type in the hierarchy can share the
same ID value.

Entity rows in Cloud Datastore store their respective types'
`DiscriminatorValue` in a field specified by the root superclass’s
`DiscriminatorField` (`pet_type` in this case). Reads and queries using
a given type parameter will match each entity with its specific type.
For example, reading a `List<Pet>` will produce a list containing
instances of all 3 types. However, reading a `List<Dog>` will produce a
list containing only `Dog` and `Pug` instances. You can include the
`pet_type` discrimination field in your Java entities, but its type must
be convertible to a collection or array of `String`. Any value set in
the discrimination field will be overwritten upon write to Cloud
Datastore.

### Relationships

There are three ways to represent relationships between entities that
are described in this section:

  - Embedded entities stored directly in the field of the containing
    entity

  - `@Descendant` annotated properties for one-to-many relationships

  - `@Reference` annotated properties for general relationships without
    hierarchy

  - `@LazyReference` similar to `@Reference`, but the entities are
    lazy-loaded when the property is accessed. (Note that the keys of
    the children are retrieved when the parent entity is loaded.)

#### Embedded Entities

Fields whose types are also annotated with `@Entity` are converted to
`EntityValue` and stored inside the parent entity.

Here is an example of Cloud Datastore entity containing an embedded
entity in JSON:

``` json
{
  "name" : "Alexander",
  "age" : 47,
  "child" : {"name" : "Philip"  }
}
```

This corresponds to a simple pair of Java entities:

``` java
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity("parents")
public class Parent {
  @Id
  String name;

  Child child;
}

@Entity
public class Child {
  String name;
}
```

`Child` entities are not stored in their own kind. They are stored in
their entirety in the `child` field of the `parents` kind.

Multiple levels of embedded entities are supported.

<div class="note">

Embedded entities don’t need to have `@Id` field, it is only required
for top level entities.

</div>

Example:

Entities can hold embedded entities that are their own type. We can
store trees in Cloud Datastore using this feature:

``` java
import com.google.cloud.spring.data.datastore.core.mapping.Embedded;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity
public class EmbeddableTreeNode {
  @Id
  long value;

  EmbeddableTreeNode left;

  EmbeddableTreeNode right;

  Map<String, Long> longValues;

  Map<String, List<Timestamp>> listTimestamps;

  public EmbeddableTreeNode(long value, EmbeddableTreeNode left, EmbeddableTreeNode right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }
}
```

##### Maps

Maps will be stored as embedded entities where the key values become the
field names in the embedded entity. The value types in these maps can be
any regularly supported property type, and the key values will be
converted to String using the configured converters.

Also, a collection of entities can be embedded; it will be converted to
`ListValue` on write.

Example:

Instead of a binary tree from the previous example, we would like to
store a general tree (each node can have an arbitrary number of
children) in Cloud Datastore. To do that, we need to create a field of
type `List<EmbeddableTreeNode>`:

``` java
import com.google.cloud.spring.data.datastore.core.mapping.Embedded;
import org.springframework.data.annotation.Id;

public class EmbeddableTreeNode {
  @Id
  long value;

  List<EmbeddableTreeNode> children;

  Map<String, EmbeddableTreeNode> siblingNodes;

  Map<String, Set<EmbeddableTreeNode>> subNodeGroups;

  public EmbeddableTreeNode(List<EmbeddableTreeNode> children) {
    this.children = children;
  }
}
```

Because Maps are stored as entities, they can further hold embedded
entities:

  - Singular embedded objects in the value can be stored in the values
    of embedded Maps.

  - Collections of embedded objects in the value can also be stored as
    the values of embedded Maps.

  - Maps in the value are further stored as embedded entities with the
    same rules applied recursively for their values.

#### Ancestor-Descendant Relationships

Parent-child relationships are supported via the `@Descendants`
annotation.

Unlike embedded children, descendants are fully-formed entities residing
in their own kinds. The parent entity does not have an extra field to
hold the descendant entities. Instead, the relationship is captured in
the descendants' keys, which refer to their parent entities:

``` java
import com.google.cloud.spring.data.datastore.core.mapping.Descendants;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity("orders")
public class ShoppingOrder {
  @Id
  long id;

  @Descendants
  List<Item> items;
}

@Entity("purchased_item")
public class Item {
  @Id
  Key purchasedItemKey;

  String name;

  Timestamp timeAddedToOrder;
}
```

For example, an instance of a GQL key-literal representation for `Item`
would also contain the parent `ShoppingOrder` ID value:

    Key(orders, '12345', purchased_item, 'eggs')

The GQL key-literal representation for the parent `ShoppingOrder` would
be:

    Key(orders, '12345')

The Cloud Datastore entities exist separately in their own kinds.

The `ShoppingOrder`:

    {
      "id" : 12345
    }

The two items inside that order:

    {
      "purchasedItemKey" : Key(orders, '12345', purchased_item, 'eggs'),
      "name" : "eggs",
      "timeAddedToOrder" : "2014-09-27 12:30:00.45-8:00"
    }
    
    {
      "purchasedItemKey" : Key(orders, '12345', purchased_item, 'sausage'),
      "name" : "sausage",
      "timeAddedToOrder" : "2014-09-28 11:30:00.45-9:00"
    }

The parent-child relationship structure of objects is stored in Cloud
Datastore using Datastore’s [ancestor
relationships](https://cloud.google.com/datastore/docs/concepts/entities#ancestor_paths).
Because the relationships are defined by the Ancestor mechanism, there
is no extra column needed in either the parent or child entity to store
this relationship. The relationship link is part of the descendant
entity’s key value. These relationships can be many levels deep.

Properties holding child entities must be collection-like, but they can
be any of the supported inter-convertible collection-like types that are
supported for regular properties such as `List`, arrays, `Set`, etc…​
Child items must have `Key` as their ID type because Cloud Datastore
stores the ancestor relationship link inside the keys of the children.

Reading or saving an entity automatically causes all subsequent levels
of children under that entity to be read or saved, respectively. If a
new child is created and added to a property annotated `@Descendants`
and the key property is left null, then a new key will be allocated for
that child. The ordering of the retrieved children may not be the same
as the ordering in the original property that was saved.

Child entities cannot be moved from the property of one parent to that
of another unless the child’s key property is set to `null` or a value
that contains the new parent as an ancestor. Since Cloud Datastore
entity keys can have multiple parents, it is possible that a child
entity appears in the property of multiple parent entities. Because
entity keys are immutable in Cloud Datastore, to change the key of a
child you must delete the existing one and re-save it with the new key.

#### Key Reference Relationships

General relationships can be stored using the `@Reference` annotation.

``` java
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Id;

@Entity
public class ShoppingOrder {
  @Id
  long id;

  @Reference
  List<Item> items;

  @Reference
  Item specialSingleItem;
}

@Entity
public class Item {
  @Id
  Key purchasedItemKey;

  String name;

  Timestamp timeAddedToOrder;
}
```

`@Reference` relationships are between fully-formed entities residing in
their own kinds. The relationship between `ShoppingOrder` and `Item`
entities are stored as a Key field inside `ShoppingOrder`, which are
resolved to the underlying Java entity type by Spring Data Cloud
Datastore:

    {
      "id" : 12345,
      "specialSingleItem" : Key(item, "milk"),
      "items" : [ Key(item, "eggs"), Key(item, "sausage") ]
    }

Reference properties can either be singular or collection-like. These
properties correspond to actual columns in the entity and Cloud
Datastore Kind that hold the key values of the referenced entities. The
referenced entities are full-fledged entities of other Kinds.

Similar to the `@Descendants` relationships, reading or writing an
entity will recursively read or write all of the referenced entities at
all levels. If referenced entities have `null` ID values, then they will
be saved as new entities and will have ID values allocated by Cloud
Datastore. There are no requirements for relationships between the key
of an entity and the keys that entity holds as references. The order of
collection-like reference properties is not preserved when reading back
from Cloud Datastore.

### Datastore Operations & Template

`DatastoreOperations` and its implementation, `DatastoreTemplate`,
provides the Template pattern familiar to Spring developers.

Using the auto-configuration provided by Spring Boot Starter for
Datastore, your Spring application context will contain a fully
configured `DatastoreTemplate` object that you can autowire in your
application:

``` java
@SpringBootApplication
public class DatastoreTemplateExample {

    @Autowired
    DatastoreTemplate datastoreTemplate;

    public void doSomething() {
        this.datastoreTemplate.deleteAll(Trader.class);
        //...
        Trader t = new Trader();
        //...
        this.datastoreTemplate.save(t);
        //...
        List<Trader> traders = datastoreTemplate.findAll(Trader.class);
        //...
    }
}
```

The Template API provides convenience methods for:

  - Write operations (saving and deleting)

  - Read-write transactions

#### GQL Query

In addition to retrieving entities by their IDs, you can also submit
queries.

``` java
  <T> Iterable<T> query(Query<? extends BaseEntity> query, Class<T> entityClass);

  <A, T> Iterable<T> query(Query<A> query, Function<A, T> entityFunc);

  Iterable<Key> queryKeys(Query<Key> query);
```

These methods, respectively, allow querying for:

  - entities mapped by a given entity class using all the same mapping
    and converting features

  - arbitrary types produced by a given mapping function

  - only the Cloud Datastore keys of the entities found by the query

#### Find by ID(s)

Using `DatastoreTemplate` you can find entities by id. For example:

``` java
Trader trader = this.datastoreTemplate.findById("trader1", Trader.class);

List<Trader> traders = this.datastoreTemplate.findAllById(Arrays.asList("trader1", "trader2"), Trader.class);

List<Trader> allTraders = this.datastoreTemplate.findAll(Trader.class);
```

Cloud Datastore uses key-based reads with strong consistency, but
queries with eventual consistency. In the example above the first two
reads utilize keys, while the third is run by using a query based on the
corresponding Kind of `Trader`.

##### Indexes

By default, all fields are indexed. To disable indexing on a particular
field, `@Unindexed` annotation can be used.

Example:

``` java
import com.google.cloud.spring.data.datastore.core.mapping.Unindexed;

public class ExampleItem {
    long indexedField;

    @Unindexed
    long unindexedField;

    @Unindexed
    List<String> unindexedListField;
}
```

When using queries directly or via Query Methods, Cloud Datastore
requires [composite custom
indexes](https://cloud.google.com/datastore/docs/concepts/indexes) if
the select statement is not `SELECT *` or if there is more than one
filtering condition in the `WHERE` clause.

##### Read with offsets, limits, and sorting

`DatastoreRepository` and custom-defined entity repositories implement
the Spring Data `PagingAndSortingRepository`, which supports offsets and
limits using page numbers and page sizes. Paging and sorting options are
also supported in `DatastoreTemplate` by supplying a
`DatastoreQueryOptions` to `findAll`.

##### Partial read

This feature is not supported yet.

#### Write / Update

The write methods of `DatastoreOperations` accept a POJO and writes all
of its properties to Datastore. The required Datastore kind and entity
metadata is obtained from the given object’s actual type.

If a POJO was retrieved from Datastore and its ID value was changed and
then written or updated, the operation will occur as if against a row
with the new ID value. The entity with the original ID value will not be
affected.

``` java
Trader t = new Trader();
this.datastoreTemplate.save(t);
```

The `save` method behaves as update-or-insert.

##### Partial Update

This feature is not supported yet.

#### Transactions

Read and write transactions are provided by `DatastoreOperations` via
the `performTransaction` method:

``` java
@Autowired
DatastoreOperations myDatastoreOperations;

public String doWorkInsideTransaction() {
  return myDatastoreOperations.performTransaction(
    transactionDatastoreOperations -> {
      // Work with transactionDatastoreOperations here.
      // It is also a DatastoreOperations object.

      return "transaction completed";
    }
  );
}
```

The `performTransaction` method accepts a `Function` that is provided an
instance of a `DatastoreOperations` object. The final returned value and
type of the function is determined by the user. You can use this object
just as you would a regular `DatastoreOperations` with an exception:

  - It cannot perform sub-transactions.

Because of Cloud Datastore’s consistency guarantees, there are
[limitations](https://cloud.google.com/datastore/docs/concepts/transactions#what_can_be_done_in_a_transaction)
to the operations and relationships among entities used inside
transactions.

##### Declarative Transactions with @Transactional Annotation

This feature requires a bean of `DatastoreTransactionManager`, which is
provided when using `spring-cloud-gcp-starter-data-datastore`.

`DatastoreTemplate` and `DatastoreRepository` support running methods
with the `@Transactional`
[annotation](https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#transaction-declarative)
as transactions. If a method annotated with `@Transactional` calls
another method also annotated, then both methods will work within the
same transaction. `performTransaction` cannot be used in
`@Transactional` annotated methods because Cloud Datastore does not
support transactions within transactions.

#### Read-Write Support for Maps

You can work with Maps of type `Map<String, ?>` instead of with entity
objects by directly reading and writing them to and from Cloud
Datastore.

<div class="note">

This is a different situation than using entity objects that contain Map
properties.

</div>

The map keys are used as field names for a Datastore entity and map
values are converted to Datastore supported types. Only simple types are
supported (i.e. collections are not supported). Converters for custom
value types can be added (see [Custom types](#_custom_types) section).

Example:

``` java
Map<String, Long> map = new HashMap<>();
map.put("field1", 1L);
map.put("field2", 2L);
map.put("field3", 3L);

keyForMap = datastoreTemplate.createKey("kindName", "id");

//write a map
datastoreTemplate.writeMap(keyForMap, map);

//read a map
Map<String, Long> loadedMap = datastoreTemplate.findByIdAsMap(keyForMap, Long.class);
```

### Repositories

[Spring Data
Repositories](https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#repositories)
are an abstraction that can reduce boilerplate code.

For example:

``` java
public interface TraderRepository extends DatastoreRepository<Trader, String> {
}
```

Spring Data generates a working implementation of the specified
interface, which can be autowired into an application.

The `Trader` type parameter to `DatastoreRepository` refers to the
underlying domain type. The second type parameter, `String` in this
case, refers to the type of the key of the domain type.

``` java
public class MyApplication {

    @Autowired
    TraderRepository traderRepository;

    public void demo() {

        this.traderRepository.deleteAll();
        String traderId = "demo_trader";
        Trader t = new Trader();
        t.traderId = traderId;
        this.tradeRepository.save(t);

        Iterable<Trader> allTraders = this.traderRepository.findAll();

        int count = this.traderRepository.count();
    }
}
```

Repositories allow you to define custom Query Methods (detailed in the
following sections) for retrieving, counting, and deleting based on
filtering and paging parameters. Filtering parameters can be of types
supported by your configured custom converters.

#### Query methods by convention

``` java
public interface TradeRepository extends DatastoreRepository<Trade, String[]> {
  List<Trader> findByAction(String action);

  //throws an exception if no results
  Trader findOneByAction(String action);

  //because of the annotation, returns null if no results
  @Nullable
  Trader getByAction(String action);

  Optional<Trader> getOneByAction(String action);

  int countByAction(String action);

  boolean existsByAction(String action);

  List<Trade> findTop3ByActionAndSymbolAndPriceGreaterThanAndPriceLessThanOrEqualOrderBySymbolDesc(
            String action, String symbol, double priceFloor, double priceCeiling);

  Page<TestEntity> findByAction(String action, Pageable pageable);

  Slice<TestEntity> findBySymbol(String symbol, Pageable pageable);

  List<TestEntity> findBySymbol(String symbol, Sort sort);

  Stream<TestEntity> findBySymbol(String symbol);
}
```

In the example above the [query
methods](https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#repositories.query-methods)
in `TradeRepository` are generated based on the name of the methods
using the [Spring Data Query creation naming
convention](https://docs.spring.io/spring-data/data-commons/docs/current/reference/html#repositories.query-methods.query-creation).

<div class="note">

You can refer to nested fields using [Spring Data JPA Property
Expressions](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-property-expressions)

</div>

Cloud Datastore only supports filter components joined by AND, and the
following operations:

  - `equals`

  - `greater than or equals`

  - `greater than`

  - `less than or equals`

  - `less than`

  - `is null`

After writing a custom repository interface specifying just the
signatures of these methods, implementations are generated for you and
can be used with an auto-wired instance of the repository. Because of
Cloud Datastore’s requirement that explicitly selected fields must all
appear in a composite index together, `find` name-based query methods
are run as `SELECT *`.

Delete queries are also supported. For example, query methods such as
`deleteByAction` or `removeByAction` delete entities found by
`findByAction`. Delete queries are run as separate read and delete
operations instead of as a single transaction because Cloud Datastore
cannot query in transactions unless ancestors for queries are specified.
As a result, `removeBy` and `deleteBy` name-convention query methods
cannot be used inside transactions via either `performInTransaction` or
`@Transactional` annotation.

Delete queries can have the following return types:

  - An integer type that is the number of entities deleted

  - A collection of entities that were deleted

  - 'void'

Methods can have `org.springframework.data.domain.Pageable` parameter to
control pagination and sorting, or
`org.springframework.data.domain.Sort` parameter to control sorting
only. See [Spring Data
documentation](https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#repositories.query-methods)
for details.

For returning multiple items in a repository method, we support Java
collections as well as `org.springframework.data.domain.Page` and
`org.springframework.data.domain.Slice`. If a method’s return type is
`org.springframework.data.domain.Page`, the returned object will include
current page, total number of results and total number of pages.

<div class="note">

Methods that return `Page` run an additional query to compute total
number of pages. Methods that return `Slice`, on the other hand, do not
run any additional queries and, therefore, are much more efficient.

</div>

#### Empty result handling in repository methods

Java `java.util.Optional` can be used to indicate the potential absence
of a return value.

Alternatively, query methods can return the result without a wrapper. In
that case the absence of a query result is indicated by returning
`null`. Repository methods returning collections are guaranteed never to
return `null` but rather the corresponding empty collection.

<div class="note">

You can enable nullability checks. For more details please see [Spring
Framework’s nullability
docs](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#null-safety).

</div>

#### Query by example

Query by Example is an alternative querying technique. It enables
dynamic query generation based on a user-provided object. See [Spring
Data
Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example)
for details.

##### Unsupported features:

1.  Currently, only equality queries are supported (no ignore-case
    matching, regexp matching, etc.).

2.  Per-field matchers are not supported.

3.  Embedded entities matching is not supported.

4.  Projection is not supported.

For example, if you want to find all users with the last name "Smith",
you would use the following code:

``` java
userRepository.findAll(
    Example.of(new User(null, null, "Smith"))
```

`null` fields are not used in the filter by default. If you want to
include them, you would use the following code:

``` java
userRepository.findAll(
    Example.of(new User(null, null, "Smith"), ExampleMatcher.matching().withIncludeNullValues())
```

You can also extend query specification initially defined by an example
in FluentQuery’s chaining style:

    userRepository.findBy(
        Example.of(new User(null, null, "Smith")), q -> q.sortBy(Sort.by("firstName")).firstValue());
    
    userRepository.findBy(
        Example.of(new User(null, null, "Smith")), FetchableFluentQuery::stream);

#### Custom GQL query methods

Custom GQL queries can be mapped to repository methods in one of two
ways:

  - `namedQueries` properties file

  - using the `@Query` annotation

##### Query methods with annotation

Using the `@Query` annotation:

The names of the tags of the GQL correspond to the `@Param` annotated
names of the method parameters.

``` java
public interface TraderRepository extends DatastoreRepository<Trader, String> {

  @Query("SELECT * FROM traders WHERE name = @trader_name")
  List<Trader> tradersByName(@Param("trader_name") String traderName);

  @Query("SELECT * FROM traders WHERE name = @trader_name")
  Stream<Trader> tradersStreamByName(@Param("trader_name") String traderName);

  @Query("SELECT * FROM  test_entities_ci WHERE name = @trader_name")
  TestEntity getOneTestEntity(@Param("trader_name") String traderName);

  @Query("SELECT * FROM traders WHERE name = @trader_name")
  List<Trader> tradersByNameSort(@Param("trader_name") String traderName, Sort sort);

  @Query("SELECT * FROM traders WHERE name = @trader_name")
  Slice<Trader> tradersByNameSlice(@Param("trader_name") String traderName, Pageable pageable);

  @Query("SELECT * FROM traders WHERE name = @trader_name")
  Page<Trader> tradersByNamePage(@Param("trader_name") String traderName, Pageable pageable);
}
```

When the return type is `Slice` or `Pageable`, the result set cursor
that points to the position just after the page is preserved in the
returned `Slice` or `Page` object. To take advantage of the cursor to
query for the next page or slice, use `result.getPageable().next()`.

<div class="note">

`Page` requires the total count of entities produced by the query.
Therefore, the first query will have to retrieve all of the records just
to count them. Instead, we recommend using the `Slice` return type,
because it does not require an additional count query.

</div>

``` java
 Slice<Trader> slice1 = tradersByNamePage("Dave", PageRequest.of(0, 5));
 Slice<Trader> slice2 = tradersByNamePage("Dave", slice1.getPageable().next());
```

<div class="note">

You cannot use these Query Methods in repositories where the type
parameter is a subclass of another class annotated with
`DiscriminatorField`.

</div>

The following parameter types are supported:

  - `com.google.cloud.Timestamp`

  - `com.google.cloud.datastore.Blob`

  - `com.google.cloud.datastore.Key`

  - `com.google.cloud.datastore.Cursor`

  - `java.lang.Boolean`

  - `java.lang.Double`

  - `java.lang.Long`

  - `java.lang.String`

  - `enum` values. These are queried as `String` values.

With the exception of `Cursor`, array forms of each of the types are
also supported.

If you would like to obtain the count of items of a query or if there
are any items returned by the query, set the `count = true` or `exists =
true` properties of the `@Query` annotation, respectively. The return
type of the query method in these cases should be an integer type or a
boolean type.

Cloud Datastore provides provides the `SELECT __key__ FROM …​` special
column for all kinds that retrieves the `Key` of each row. Selecting
this special `__key__` column is especially useful and efficient for
`count` and `exists` queries.

You can also query for non-entity types:

``` java
@Query(value = "SELECT __key__ from test_entities_ci")
List<Key> getKeys();

@Query(value = "SELECT __key__ from test_entities_ci limit 1")
Key getKey();
```

In order to use `@Id` annotated fields in custom queries, use `__key__`
keyword for the field name. The parameter type should be of `Key`, as in
the following example.

Repository method:

``` java
@Query("select * from  test_entities_ci where size = @size and __key__ = @id")
LinkedList<TestEntity> findEntities(@Param("size") long size, @Param("id") Key id);
```

Generate a key from id value using `DatastoreTemplate.createKey` method
and use it as a parameter for the repository method:

``` java
this.testEntityRepository.findEntities(1L, datastoreTemplate.createKey(TestEntity.class, 1L))
```

SpEL can be used to provide GQL parameters:

``` java
@Query("SELECT * FROM |com.example.Trade| WHERE trades.action = @act
  AND price > :#{#priceRadius * -1} AND price < :#{#priceRadius * 2}")
List<Trade> fetchByActionNamedQuery(@Param("act") String action, @Param("priceRadius") Double r);
```

Kind names can be directly written in the GQL annotations. Kind names
can also be resolved from the `@Entity` annotation on domain classes.

In this case, the query should refer to table names with fully qualified
class names surrounded by `|` characters: `|fully.qualified.ClassName|`.
This is useful when SpEL expressions appear in the kind name provided to
the `@Entity` annotation. For example:

``` java
@Query("SELECT * FROM |com.example.Trade| WHERE trades.action = @act")
List<Trade> fetchByActionNamedQuery(@Param("act") String action);
```

##### Query methods with named queries properties

You can also specify queries with Cloud Datastore parameter tags and
SpEL expressions in properties files.

By default, the `namedQueriesLocation` attribute on
`@EnableDatastoreRepositories` points to the
`META-INF/datastore-named-queries.properties` file. You can specify the
query for a method in the properties file by providing the GQL as the
value for the "interface.method" property:

<div class="note">

You cannot use these Query Methods in repositories where the type
parameter is a subclass of another class annotated with
`DiscriminatorField`.

</div>

``` properties
Trader.fetchByName=SELECT * FROM traders WHERE name = @tag0
```

``` java
public interface TraderRepository extends DatastoreRepository<Trader, String> {

    // This method uses the query from the properties file instead of one generated based on name.
    List<Trader> fetchByName(@Param("tag0") String traderName);

}
```

#### Transactions

These transactions work very similarly to those of
`DatastoreOperations`, but is specific to the repository’s domain type
and provides repository functions instead of template functions.

For example, this is a read-write transaction:

``` java
@Autowired
DatastoreRepository myRepo;

public String doWorkInsideTransaction() {
  return myRepo.performTransaction(
    transactionDatastoreRepo -> {
      // Work with the single-transaction transactionDatastoreRepo here.
      // This is a DatastoreRepository object.

      return "transaction completed";
    }
  );
}
```

#### Projections

Spring Data Cloud Datastore supports
[projections](https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#projections).
You can define projection interfaces based on domain types and add query
methods that return them in your repository:

``` java
public interface TradeProjection {

    String getAction();

    @Value("#{target.symbol + ' ' + target.action}")
    String getSymbolAndAction();
}

public interface TradeRepository extends DatastoreRepository<Trade, Key> {

    List<Trade> findByTraderId(String traderId);

    List<TradeProjection> findByAction(String action);

    @Query("SELECT action, symbol FROM trades WHERE action = @action")
    List<TradeProjection> findByQuery(String action);
}
```

Projections can be provided by name-convention-based query methods as
well as by custom GQL queries. If using custom GQL queries, you can
further restrict the fields retrieved from Cloud Datastore to just those
required by the projection. However, custom select statements (those not
using `SELECT *`) require composite indexes containing the selected
fields.

Properties of projection types defined using SpEL use the fixed name
`target` for the underlying domain object. As a result, accessing
underlying properties take the form `target.<property-name>`.

#### REST Repositories

When running with Spring Boot, repositories can be exposed as REST
services by simply adding this dependency to your pom file:

``` xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```

If you prefer to configure parameters (such as path), you can use
`@RepositoryRestResource` annotation:

``` java
@RepositoryRestResource(collectionResourceRel = "trades", path = "trades")
public interface TradeRepository extends DatastoreRepository<Trade, String[]> {
}
```

For example, you can retrieve all `Trade` objects in the repository by
using `curl http://<server>:<port>/trades`, or any specific trade via
`curl http://<server>:<port>/trades/<trader_id>`.

You can also write trades using `curl -XPOST -H"Content-Type:
application/json" -d@test.json http://<server>:<port>/trades/` where the
file `test.json` holds the JSON representation of a `Trade` object.

To delete trades, you can use `curl -XDELETE
http://<server>:<port>/trades/<trader_id>`

### Events

Spring Data Cloud Datastore publishes events extending the Spring
Framework’s `ApplicationEvent` to the context that can be received by
`ApplicationListener` beans you register.

| Type                  | Description                                                                        | Contents                                                                                                                              |
| --------------------- | ---------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| `AfterFindByKeyEvent` | Published immediately after read by-key operations are run by `DatastoreTemplate`  | The entities read from Cloud Datastore and the original keys in the request.                                                          |
| `AfterQueryEvent`     | Published immediately after read byquery operations are run by `DatastoreTemplate` | The entities read from Cloud Datastore and the original query in the request.                                                         |
| `BeforeSaveEvent`     | Published immediately before save operations are run by `DatastoreTemplate`        | The entities to be sent to Cloud Datastore and the original Java objects being saved.                                                 |
| `AfterSaveEvent`      | Published immediately after save operations are run by `DatastoreTemplate`         | The entities sent to Cloud Datastore and the original Java objects being saved.                                                       |
| `BeforeDeleteEvent`   | Published immediately before delete operations are run by `DatastoreTemplate`      | The keys to be sent to Cloud Datastore. The target entities, ID values, or entity type originally specified for the delete operation. |
| `AfterDeleteEvent`    | Published immediately after delete operations are run by `DatastoreTemplate`       | The keys sent to Cloud Datastore. The target entities, ID values, or entity type originally specified for the delete operation.       |

### Auditing

Spring Data Cloud Datastore supports the `@LastModifiedDate` and
`@LastModifiedBy` auditing annotations for properties:

``` java
@Entity
public class SimpleEntity {
    @Id
    String id;

    @LastModifiedBy
    String lastUser;

    @LastModifiedDate
    DateTime lastTouched;
}
```

Upon insert, update, or save, these properties will be set automatically
by the framework before Datastore entities are generated and saved to
Cloud Datastore.

To take advantage of these features, add the `@EnableDatastoreAuditing`
annotation to your configuration class and provide a bean for an
`AuditorAware<A>` implementation where the type `A` is the desired
property type annotated by `@LastModifiedBy`:

``` java
@Configuration
@EnableDatastoreAuditing
public class Config {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("YOUR_USERNAME_HERE");
    }
}
```

The `AuditorAware` interface contains a single method that supplies the
value for fields annotated by `@LastModifiedBy` and can be of any type.
One alternative is to use Spring Security’s `User` type:

``` java
class SpringSecurityAuditorAware implements AuditorAware<User> {

  public Optional<User> getCurrentAuditor() {

    return Optional.ofNullable(SecurityContextHolder.getContext())
              .map(SecurityContext::getAuthentication)
              .filter(Authentication::isAuthenticated)
              .map(Authentication::getPrincipal)
              .map(User.class::cast);
  }
}
```

You can also set a custom provider for properties annotated
`@LastModifiedDate` by providing a bean for `DateTimeProvider` and
providing the bean name to `@EnableDatastoreAuditing(dateTimeProviderRef
= "customDateTimeProviderBean")`.

### Partitioning Data by Namespace

You can [partition your data by using more than one
namespace](https://cloud.google.com/datastore/docs/concepts/multitenancy).
This is the recommended method for multi-tenancy in Cloud Datastore.

``` java
    @Bean
    public DatastoreNamespaceProvider namespaceProvider() {
        // return custom Supplier of a namespace string.
    }
```

The `DatastoreNamespaceProvider` is a synonym for `Supplier<String>`. By
providing a custom implementation of this bean (for example, supplying a
thread-local namespace name), you can direct your application to use
multiple namespaces. Every read, write, query, and transaction you
perform will utilize the namespace provided by this supplier.

Note that your provided namespace in `application.properties` will be
ignored if you define a namespace provider bean.

### Spring Boot Actuator Support

#### Cloud Datastore Health Indicator

If you are using Spring Boot Actuator, you can take advantage of the
Cloud Datastore health indicator called `datastore`. The health
indicator will verify whether Cloud Datastore is up and accessible by
your application. To enable it, all you need to do is add the [Spring
Boot
Actuator](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready)
to your project.

``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Sample

A [Simple Spring Boot
Application](https://github.com/GoogleCloudPlatform/spring-cloud-gcp/tree/main/spring-cloud-gcp-samples/spring-cloud-gcp-data-datastore-basic-sample)
and more advanced [Sample Spring Boot
Application](https://github.com/GoogleCloudPlatform/spring-cloud-gcp/tree/main/spring-cloud-gcp-samples/spring-cloud-gcp-data-datastore-sample)
are provided to show how to use the Spring Data Cloud Datastore starter
and template.

### Test

`Testcontainers` provides a `gcloud` module which offers `DatastoreEmulatorContainer`. See more at the [docs](https://www.testcontainers.org/modules/gcloud/#datastore)